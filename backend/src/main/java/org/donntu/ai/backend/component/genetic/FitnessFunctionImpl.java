package org.donntu.ai.backend.component.genetic;

import org.donntu.ai.backend.utils.NumericUtil;
import org.springframework.stereotype.Component;

@Component
public class FitnessFunctionImpl implements FitnessFunction {
    @Override
    public int getArity() {
        return 0;
    }

    @Override
    public double getFunctionValue(StringBuffer chromosome) {
        double x = NumericUtil.binaryStringToDouble(chromosome);
        double v = Math.pow(x, 3) - 4 * Math.pow(x, 2) + 7.0;
//        System.out.println("f(" + x + ") = " + v);
        return v;
    }
}
