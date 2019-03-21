package org.donntu.ai.backend.component.genetic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.utils.NumericUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chromosome {
    private StringBuffer binary;
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
        this.binary = NumericUtil.doubleToBinaryString(decimal);
        this.decimal = decimal;
    }

    public void setBinary(StringBuffer binary) {
        this.decimal = NumericUtil.binaryStringToDouble(binary);
        this.binary = binary;
    }

    public void decrementCopiesCount() {
        copiesCount--;
    }

    public void updateDecimal() {
        this.decimal = NumericUtil.binaryStringToDouble(binary);
    }
}
