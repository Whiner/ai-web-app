package org.donntu.ai.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.donntu.ai.backend.component.genetic.Chromosome;
import org.donntu.ai.backend.component.genetic.FitnessFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.donntu.ai.backend.utils.NumericUtil.binaryStringToDouble;
import static org.donntu.ai.backend.utils.NumericUtil.getNearestMaxTwoPower;

@Service
@Data
@RequiredArgsConstructor
public class GeneticService {
    private final FitnessFunction fitnessFunction;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private final int INTERVALS_COUNT = 1000;
    private final int MAX_CHROMOSOMES_COUNT = 300;
    private final int MIN_CHROMOSOMES_COUNT = 3;

    private double rightAnswer = 2482; //f(15)
    private double eps = 0.001;

    public Double getMaxExtremumX(
            double lowerInterval,
            double upperInterval,
            int chromosomeCount,
            int maxIterationsCount,
            double mutationChance,
            double crossingChance) throws Exception {

        final int bitsCount = getNearestMaxTwoPower((int) (INTERVALS_COUNT * (upperInterval - lowerInterval)));

        List<Chromosome> genome = generateGenome(bitsCount, chromosomeCount);
        int currentIteration = 0;

        String bestChromosome = "";
        double maxFunctionValue = -Double.MAX_VALUE;

        genome.forEach(chromosome -> chromosome.updateDecimal(lowerInterval, upperInterval));

        while (currentIteration <= maxIterationsCount) {
            reproduction(genome, lowerInterval, upperInterval);

            if(genome.size() < MIN_CHROMOSOMES_COUNT) {
                throw new Exception("Хромосомы вымерли");
            } else if(genome.size() > MAX_CHROMOSOMES_COUNT) {
                throw new Exception("Максимальное количество хромосом превышено");
            }

            for (Chromosome chromosome : genome) {
                if (random.nextDouble() <= crossingChance) {
                    crossing(chromosome, genome);
                }

                String binary = chromosome.getBinary();
                if (random.nextDouble() <= mutationChance) {
                    chromosome.setBinary(mutation(binary));
                    chromosome.updateDecimal(lowerInterval, upperInterval);
                }

                Double decimal = binaryStringToDouble(binary, lowerInterval, upperInterval);
                double functionValue = fitnessFunction.getFunctionValue(decimal);

                chromosome.setDecimal(decimal);
                chromosome.setFunctionValue(functionValue);

                if (functionValue > maxFunctionValue) {
                    bestChromosome = binary;
                    maxFunctionValue = functionValue;
                }
            }
            genome.addAll(generateGenome(bitsCount, chromosomeCount - genome.size()));
            currentIteration++;

        }
        return binaryStringToDouble(bestChromosome, lowerInterval, upperInterval);
    }

    private void crossing(Chromosome chromosome, List<Chromosome> genome) {
        Chromosome crossingPair = getCrossingPair(chromosome, genome);
        if (crossingPair != null) {
            chromosome.of(
                    crossing(
                            chromosome.getBinary(),
                            crossingPair.getBinary(),
                            generateChromosome(chromosome.getBinary().length())
                    )
            );
        }
    }

    private Chromosome getCrossingPair(Chromosome chromosome, List<Chromosome> genome) {
        if (!genome.stream().allMatch(chromosome::equals)) {
            Chromosome pair;
            do {
                pair = genome.get(random.nextInt(genome.size()));
            } while (pair.equals(chromosome));
            return pair;
        } else {
            return null;
        }
    }

    private String mutation(String chromosome) {
        StringBuilder mutableChromosome = new StringBuilder(chromosome);
        int i = random.nextInt(chromosome.length());
        char c = mutableChromosome.charAt(i);
        if (c == '0') {
            mutableChromosome.setCharAt(i, '1');
        } else {
            mutableChromosome.setCharAt(i, '0');
        }
        return mutableChromosome.toString();
    }

    private List<Chromosome> generateGenome(int bitsCount, int chromosomeCount) {
        List<Chromosome> genome = new ArrayList<>();
        for (int i = 0; i < chromosomeCount; i++) {
            genome.add(
                    new Chromosome(
                            generateChromosome(bitsCount)
                    ));
        }
        return genome;
    }

    private String generateChromosome(int bitsCount) {
        StringBuilder chromosome = new StringBuilder();
        for (int j = 0; j < bitsCount; j++) {
            if (random.nextDouble() <= 0.5) {
                chromosome.append('1');
            } else {
                chromosome.append('0');
            }
        }
        chromosome.trimToSize();
        return chromosome.toString();
    }

    /**
     * считает количество появлений каждой хромосомы для кроссинговера
     *
     * @param genome геном
     */
    private void reproduction(List<Chromosome> genome, double lowerInterval, double upperInterval) {
        int populationPower = genome.size();
        double functionValuesSum = genome
                .stream()
                .mapToDouble(gen -> {
                    double functionValue = fitnessFunction.getFunctionValue(
                            binaryStringToDouble(gen.getBinary(), lowerInterval, upperInterval)
                    );
                    gen.setFunctionValue(functionValue);
                    return functionValue;
                })
                .sum();

        List<Chromosome> newGenome = new ArrayList<>();

        genome.forEach(chromosome -> {
            long count = Math.round((chromosome.getFunctionValue() / functionValuesSum) * populationPower);
            for (int i = 0; i < count; i++) {
                newGenome.add(chromosome.copy());
            }

        });

        genome.clear();
        genome.addAll(newGenome);
    }

    private String crossing(String chromosome1, String chromosome2, String model) {
        char[] chars = model.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '0') {
                builder.append(chromosome1.charAt(i));
            } else {
                builder.append(chromosome2.charAt(i));
            }
        }
        return builder.toString();
    }
}
