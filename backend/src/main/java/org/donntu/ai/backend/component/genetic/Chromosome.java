package org.donntu.ai.backend.component.genetic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.utils.NumericUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chromosome {
    private String binary;
    private double decimal;

    private double functionValue;
    private int copiesCount;

    public Chromosome(StringBuffer binary) {
        binary.trimToSize();
        setBinary(binary);
    }

    public Chromosome(double decimal) {
        setDecimal(decimal);
    }

    public void setDecimal(double decimal) {
        this.binary = NumericUtil.doubleToBinaryString(decimal).toString();
        this.decimal = decimal;
    }

    public void setBinary(StringBuffer binary) {
        this.decimal = NumericUtil.binaryStringToDouble(binary);
        this.binary = binary.toString();
    }

    public void decrementCopiesCount() {
        copiesCount--;
    }

    public void updateDecimal() {
        this.decimal = NumericUtil.binaryStringToDouble(getBinary());
    }

    public StringBuffer getBinary() {
        return new StringBuffer(binary);
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

    public void of(StringBuffer crossing) {
        setBinary(crossing);
    }
}
