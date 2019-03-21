package org.donntu.ai.backend.service;

import lombok.Data;
import org.donntu.ai.backend.component.enums.CrossingType;
import org.donntu.ai.backend.component.enums.SelectionType;
import org.donntu.ai.backend.component.genetic.FitnessFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Data
public class GeneticService {
    private final FitnessFunction fitnessFunction;

    private long timeToSelection = 0;
    private long timeToCrossing = 0;
    private long timeToMutate = 0;
    private long timeToFF = 0;

    private final double CHANCE_TO_FULLNESS = 0.999d;
    private final SelectionType DEFAULT_SELECTION_TYPE = SelectionType.TOURNEY;
    private final CrossingType DEFAULT_CROSSING_TYPE = CrossingType.ONE_POINT_RECOMBINATION;
    private final boolean DEFAULT_USE_MUTATION = true;
    private final long DEFAULT_GENERATION_COUNT = 10000L;

    private final int OCTET_LENGTH = 64; // for long
    private final int MASK_FOR_MOD = OCTET_LENGTH - 1;
    private final int SHIFT_FOR_DIVISION;

    private int genomeLength; //Длина генома в битах
    private long generationCount; //Кол-во поколений
    private int individualCount; //Кол-во Геномов(Индивидов,Особей) в поколении
    private SelectionType selectionType; //Тип Селекции
    private CrossingType crossingType; //Тип Скрещивания
    private boolean useMutation; //Использовать мутацю
    private double mutationPercent; //Как часто происходит мутация
    private int sizeOfArray;
    private long[][] genomeListParents;
    private long[][] genomeListOffspring;
    private long[] actual;
    private long[] fitnessFunctionResult;
    private long currentGeneration = 0;

    private Random random = new Random(System.currentTimeMillis());

    @Autowired
    public GeneticService(FitnessFunction fitnessFunction) {
        int shiftForDivision = 0;
        int tmp = OCTET_LENGTH;
        while (tmp > 1) {
            tmp >>= 1;
            shiftForDivision++;
        }
        this.SHIFT_FOR_DIVISION = shiftForDivision;

        this.fitnessFunction = fitnessFunction;
        this.genomeLength = fitnessFunction.getArity();

        toDefaultSettings();

        this.sizeOfArray = (int) Math.ceil((double) this.genomeLength / OCTET_LENGTH);
        this.individualCount = (int) (1 + Math.log(1 / Math.pow(1 - CHANCE_TO_FULLNESS, 1.0 / genomeLength)) / Math.log(2));
        this.mutationPercent = genomeLength * (1 - Math.pow((1 - 10 * Math.pow((1.0 / 2.0), (genomeLength - 1))), (1.0 / genomeLength)));
    }

    public void toDefaultSettings() {
        this.generationCount = DEFAULT_GENERATION_COUNT;
        this.selectionType = DEFAULT_SELECTION_TYPE;
        this.crossingType = DEFAULT_CROSSING_TYPE;
        this.useMutation = DEFAULT_USE_MUTATION;
    }

    // Main loop
    public long[] run() {
        //Preparing structuress
        this.genomeListParents = new long[this.individualCount][];
        this.genomeListOffspring = new long[this.individualCount][];
        this.fitnessFunctionResult = new long[this.individualCount];
        this.actual = new long[this.individualCount];
        for (int i = 0; i < this.individualCount; i++) {
            this.actual[i] = -1;
        }

        //Generate 1st generation
        this.generateFirstGeneration();

        while (this.currentGeneration < this.generationCount) {

            this.selection();
            this.crossing();
            if (this.useMutation) {
                this.mutation();
            }

            long[][] tmp = this.genomeListParents;
            this.genomeListParents = this.genomeListOffspring;
            this.genomeListOffspring = tmp;

            this.currentGeneration++;
        }

        long bestFitnessFunctionResult = 0;
        long[] bestGenom = null;
        for (long[] genom : this.genomeListParents) {
            long fitnessFunctionResult = 0L;//this.fitnessFunction.getFunctionValue(genom);
            if (bestFitnessFunctionResult <= fitnessFunctionResult) {
                bestGenom = genom;
                bestFitnessFunctionResult = fitnessFunctionResult;
            }
        }

        return bestGenom;
    }

    // Generate First Generation
    private void generateFirstGeneration() {
        for (int i = 0; i < this.individualCount; i++) {
            this.genomeListParents[i] = this.generateGenom();
        }
    }

    // Generate Genom - Generate 1 genom
    private long[] generateGenom() {
        long[] result = new long[this.sizeOfArray];
        for (int i = 0; i < this.sizeOfArray; i++) {
            result[i] = this.random.nextLong();
        }
        return result;
    }

    // Selection - Select genoms for crossing
    private void selection() {
        long old = System.currentTimeMillis(); // time

        switch (selectionType) {
            case ROULETTE_WHEEL: {

                float[] wheel = new float[this.individualCount];
                wheel[0] = this.getFitnessFunctionResult(0);
                for (int i = 1; i < this.individualCount; i++) {
                    wheel[i] = wheel[i - 1] + this.getFitnessFunctionResult(i);
                }
                float all = wheel[this.individualCount - 1];

                for (int i = 0; i < this.individualCount; i++) {
                    float index = Math.abs(this.random.nextFloat()) * all;

                    int l = 0;
                    int r = individualCount - 1;
                    int c = 0;
                    while (l != r) {
                        c = (l + r) >> 1;
                        if (wheel[c] < index) {
                            l = c;
                        } else {
                            r = c;
                        }
                    }
                    this.genomeListOffspring[i] = this.genomeListParents[l].clone();
                }
                break;
            }
            case TOURNEY: {
                for (int i = 0; i < this.individualCount; i++) {
                    int index1 = random.nextInt(individualCount);
                    int index2 = random.nextInt(individualCount);

                    long ffTime = System.currentTimeMillis(); // time

                    long fr1 = this.getFitnessFunctionResult(index1);
                    long fr2 = this.getFitnessFunctionResult(index2);

                    this.timeToFF += (System.currentTimeMillis() - ffTime); // time

                    this.genomeListOffspring[i] = fr1 > fr2 ? this.genomeListParents[index1].clone() : this.genomeListParents[index2].clone();
                }
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }

        this.timeToSelection += (System.currentTimeMillis() - old); // time
    }

    // Crossing - Crossing all genom in generation
    private void crossing() {
        long old = System.currentTimeMillis(); // time

        for (int i = 0; i < individualCount / 2; i++) {
            int index1 = i << 1;
            int index2 = index1 | 1;
            cross(this.genomeListOffspring[index1], this.genomeListOffspring[index2]);
        }

        this.timeToCrossing += (System.currentTimeMillis() - old); // time
    }

    // Get Fitness Function Result [with cache]
    private long getFitnessFunctionResult(int genomNumber) {
        if (this.actual[genomNumber] != this.currentGeneration) {
            this.fitnessFunctionResult[genomNumber] = 0;//this.fitnessFunction.getFunctionValue(this.genomeListParents[genomNumber]);
            this.actual[genomNumber] = this.currentGeneration;
        }
        return this.fitnessFunctionResult[genomNumber];
    }

    // Cross - Crossing 2 genom
    private void cross(long[] genom1, long[] genom2) {
        switch (crossingType) {
            case ONE_POINT_RECOMBINATION: {
                int index = this.random.nextInt(this.genomeLength);
                int outerOffset = index >> SHIFT_FOR_DIVISION;
                int innerOffset = OCTET_LENGTH - (index & MASK_FOR_MOD);
                long tmp = 0;

                if (innerOffset < 63) {
                    long mask = 1L << (innerOffset + 1) - 1;
                    long swapMask = (genom1[outerOffset] ^ genom2[outerOffset]) & mask;
                    genom1[outerOffset] ^= swapMask;
                    genom2[outerOffset] ^= swapMask;
                    outerOffset++;
                }
                for (int i = outerOffset; i < this.sizeOfArray; i++) {
                    tmp = genom1[i];
                    genom1[i] = genom2[i];
                    genom2[i] = tmp;
                }

                break;
            }
            case TWO_POINT_RECOMBINATION: {
                int index1 = this.random.nextInt(this.genomeLength);
                int index2 = this.random.nextInt(this.genomeLength);
                int startIndex = Math.min(index1, index2);
                int endIndex = Math.max(index1, index2);
                int startOuterOffset = startIndex >> SHIFT_FOR_DIVISION;
                int startInnerOffset = OCTET_LENGTH - (startIndex & MASK_FOR_MOD);
                int endOuterOffset = endIndex >> SHIFT_FOR_DIVISION;
                int endInnerOffset = OCTET_LENGTH - (endIndex & MASK_FOR_MOD);
                long tmp = 0;

                if (startInnerOffset < OCTET_LENGTH - 1) {
                    long mask = 1L << (startInnerOffset + 1) - 1;
                    long swapMask = (genom1[startOuterOffset] ^ genom2[startOuterOffset]) & mask;
                    genom1[startOuterOffset] ^= swapMask;
                    genom2[startOuterOffset] ^= swapMask;
                    startOuterOffset++;
                }
                for (int i = startOuterOffset; i <= endOuterOffset; i++) {
                    tmp = genom1[i];
                    genom1[i] = genom2[i];
                    genom2[i] = tmp;
                }
                if (endInnerOffset > 0) {
                    long mask = 1L << endInnerOffset - 1;
                    long swapMask = (genom1[endOuterOffset] ^ genom2[endOuterOffset]) & mask;
                    genom1[endOuterOffset] ^= swapMask;
                    genom2[endOuterOffset] ^= swapMask;
                }

                break;
            }
            case ELEMENTWISE_RECOMBINATION: {
                for (int outerOffset = 0; outerOffset < this.sizeOfArray; outerOffset++) {
                    long mask = this.random.nextLong();
                    long swapMask = (genom1[outerOffset] ^ genom2[outerOffset]) & mask;

                    genom1[outerOffset] ^= swapMask;
                    genom2[outerOffset] ^= swapMask;
                }
                break;
            }
            case ONE_ELEMENT_EXCHANGE: {
                int index = this.random.nextInt(this.genomeLength);
                int outerOffset = index >> SHIFT_FOR_DIVISION;
                int innerOffset = OCTET_LENGTH - (index & MASK_FOR_MOD);
                long mask = 1L << innerOffset;
                long swapMask = (genom1[outerOffset] ^ genom2[outerOffset]) & mask;

                genom1[outerOffset] ^= swapMask;
                genom2[outerOffset] ^= swapMask;
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
    }

    // Mutation - Mutate all genome in generation
    private void mutation() {
        long old = System.currentTimeMillis(); // time

        for (long[] genom : this.genomeListOffspring) {
            if (random.nextDouble() <= mutationPercent) {
                mutate(genom);
            }
        }

        this.timeToMutate += (System.currentTimeMillis() - old); // time
    }

    // Mutate - Mutate 1 genome
    private void mutate(long[] genome) {
        int index = this.random.nextInt(this.genomeLength);
        int outerOffset = index >> SHIFT_FOR_DIVISION;
        int innerOffset = (index & MASK_FOR_MOD);
        long mask = 1L << innerOffset;
        genome[outerOffset] ^= mask;
    }
}
