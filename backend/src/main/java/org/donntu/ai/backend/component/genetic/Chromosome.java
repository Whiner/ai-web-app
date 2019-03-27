package org.donntu.ai.backend.component.genetic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.utils.NumericUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chromosome {
    private String binary = null;
    private Double decimal = null;
    private Double functionValue;

    private int copiesCount;

    public Chromosome(String binary) {
        this.binary = binary;
    }

    public void decrementCopiesCount() {
        copiesCount--;
    }

    public void updateDecimal(double lowerInterval, double upperInterval) {
        this.decimal = NumericUtil.binaryStringToDouble(getBinary(), lowerInterval, upperInterval);
    }

    public String getBinary() {
        return binary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Chromosome that = (Chromosome) o;

        return binary.equals(that.binary);
    }

    @Override
    public int hashCode() {
        return binary.hashCode();
    }

    public void of(String crossing) {
        this.binary = crossing;
        this.decimal = null;
        this.functionValue = null;
    }

    public Chromosome copy() {
        return new Chromosome(binary, decimal, functionValue, copiesCount);
    }
}
