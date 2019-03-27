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

@Service
@Data
@RequiredArgsConstructor
public class GeneticServiceMy {
    private final FitnessFunction fitnessFunction;

    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private double rightAnswer = 2482; //f(15)
    private double eps = 100;

    public Chromosome getMaxFunctionValueOnInterval(
            double lowerInterval,
            double upperInterval,
            int chromosomeCount,
            int maxIterationsCount,
            double mutationChance, double crossingChance) {
        List<Chromosome> genome = generateGenome(lowerInterval, upperInterval, chromosomeCount);
        int currentIteration = 0;

        Chromosome bestChromosome = new Chromosome(-1000000.0);
        while (currentIteration <= maxIterationsCount && bestChromosome.getDecimal() != rightAnswer) {
            currentIteration++;

            reproduction(genome);

            genome.forEach(chromosome -> {
                if (random.nextDouble() <= crossingChance) {
                    crossing(chromosome, genome);
                }
                StringBuffer binary = chromosome.getBinary();
                if (random.nextDouble() <= mutationChance) {
                    String mutated = validateChromosome(
                            lowerInterval,
                            upperInterval,
                            mutation(binary)
                    ).getBinary().toString();
                    binary.delete(0, binary.length()).append(mutated);
                    chromosome.updateDecimal();
                }

                double functionValue = fitnessFunction.getFunctionValue(binary);
                if (functionValue > bestChromosome.getFunctionValue()) {
                    bestChromosome.setBinary(binary);
                    bestChromosome.setFunctionValue(functionValue);
                }
            });

//            genome.addAll(generateGenome(lowerInterval, upperInterval, chromosomeCount - genome.size()));
        }
        return bestChromosome;
    }

    private void crossing(Chromosome chromosome, List<Chromosome> genome) {
        Chromosome crossingPair = getCrossingPair(chromosome, genome);
        if (crossingPair != null) {
            chromosome.of(
                    crossing(
                            chromosome.getBinary().toString(),
                            crossingPair.getBinary().toString(),
                            random.nextInt(chromosome.getBinary().length() - 1)
                    ));
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

    private StringBuffer mutation(StringBuffer chromosome) {
        int i = random.nextInt(chromosome.length());
        char c = chromosome.charAt(i);
        if (c == '0') {
            chromosome.setCharAt(i, '1');
        } else {
            chromosome.setCharAt(i, '0');
        }
        return chromosome;
    }


    private Chromosome validateChromosome(double lowerInterval, double upperInterval, StringBuffer chromosome) {
        double value = binaryStringToDouble(chromosome);
        if (value < lowerInterval) {
            return new Chromosome(lowerInterval);
        } else if (value > upperInterval) {
            return new Chromosome(upperInterval);
        } else {
            return new Chromosome(chromosome);
        }
    }


    /**
     * возвращает рандомную хромосому
     *
     * @param genome геном
     * @return рандомная хромосома
     */
    private Chromosome getRandomChromosome(List<Chromosome> genome) {
        try {
            int i = random.nextInt(genome.size());
            Chromosome chromosome = genome.get(i);
            if (chromosome.getCopiesCount() - 1 <= 0) {
                genome.remove(chromosome);
            } else {
                chromosome.decrementCopiesCount();
            }
            return chromosome;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * формирует геном
     *
     * @param lowerInterval   нижний интервал генерации
     * @param upperInterval   верхний интервал генерации
     * @param chromosomeCount количество хромосом в геноме
     * @return список хромосом
     */
    private List<Chromosome> generateGenome(double lowerInterval, double upperInterval, int chromosomeCount) {
        List<Chromosome> genome = new ArrayList<>();
        for (int i = 0; i < chromosomeCount; i++) {
            genome.add(
                    new Chromosome(
                            random.nextDouble(
                                    lowerInterval,
                                    upperInterval
                            )
                    )
            );
        }
        return genome;
    }

    /**
     * считает количество появлений каждой хромосомы для кроссинговера
     *
     * @param genome геном
     */
    private void reproduction(List<Chromosome> genome) {
        int populationPower = genome.size();
        double functionValuesSum = genome
                .stream()
                .mapToDouble(gen -> {
                    double functionValue = fitnessFunction.getFunctionValue(gen.getBinary());
                    gen.setFunctionValue(functionValue);
                    return functionValue;
                })
                .sum();

        List<Chromosome> newGenome = new ArrayList<>();

        genome.forEach(chromosome -> {
            long count = Math.round((chromosome.getFunctionValue() / functionValuesSum) * populationPower);
            for (int i = 0; i < count; i++) {
                newGenome.add(chromosome);
            }
        });

        genome.clear();
        genome.addAll(newGenome);
    }

    /**
     * скрещивает заданные хромосомы в заданной точке кроссинговера
     *
     * @param chromosome1   первая хромосома
     * @param chromosome2   вторая хромосома
     * @param crossingIndex индекс кроссинговера
     * @return скрещенную хромосому
     */
    public StringBuffer crossing(String chromosome1, String chromosome2, int crossingIndex) {
        return new StringBuffer(chromosome1.substring(0, crossingIndex) + chromosome2.substring(crossingIndex));
    }
}
