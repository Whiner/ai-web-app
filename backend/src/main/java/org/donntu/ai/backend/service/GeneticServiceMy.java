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

    public Double getMaxFunctionValueOnInterval(
            double lowerInterval,
            double upperInterval,
            int chromosomeCount,
            int maxIterationsCount,
            double mutationChance) throws Exception {
        List<Chromosome> genome = generateGenome(lowerInterval, upperInterval, chromosomeCount);
        List<Chromosome> newGenome = new ArrayList<>();
        int currentIteration = 0;

        double lastMax = -1000000.0;
        String lastChromosome = "";

        while (currentIteration <= maxIterationsCount && lastMax != rightAnswer) {
            currentIteration++;

            reproduction(genome);

            int size = genome.stream().mapToInt(Chromosome::getCopiesCount).sum();
            while (size > 0) {
                Chromosome chromosome1 = getRandomChromosome(genome);
                size--;
                if (chromosome1 != null) {
                    Chromosome chromosome2 = getRandomChromosome(genome);
                    size--;
                    if (chromosome2 == null) {
                        newGenome.add(chromosome1);
                    } else {
                        newGenome.add(
                                validateChromosome(
                                        lowerInterval,
                                        upperInterval,
                                        crossing(
                                                chromosome1.getBinary().toString(),
                                                chromosome2.getBinary().toString(),
                                                random.nextInt(chromosome1.getBinary().length() - 1)
                                        ))
                        );
                    }
                } else {
                    throw new Exception("Что то пошло не так");
                }
            }
            genome.addAll(newGenome);
            newGenome.clear();

            for (Chromosome chromosome : genome) {
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
                if (functionValue > lastMax) {
                    lastMax = functionValue;
//                    lastChromosome = binary.toString();
                    System.out.println("f(" + binaryStringToDouble(binary) + ") = " + lastMax);
                }
            }

            genome.addAll(generateGenome(lowerInterval, upperInterval, chromosomeCount - genome.size()));
        }
        return 0.0;
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

        List<Chromosome> onRemoving = new ArrayList<>();

        genome.forEach(chromosome -> {
            Long round = Math.round((chromosome.getFunctionValue() / functionValuesSum) * populationPower);
            if(round > 0) {
                chromosome.setCopiesCount(round.intValue());
            } else {
                onRemoving.add(chromosome);
            }
        });

        onRemoving.forEach(genome::remove);
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
