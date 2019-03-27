package org.donntu.ai.backend.component.genetic;

import org.springframework.stereotype.Component;

@Component
public class FitnessFunctionImpl implements FitnessFunction {
    @Override
    public double getFunctionValue(double x) {
        return Math.pow(x, 3) - 4 * Math.pow(x, 2) + 7.0;
        //return 2 * pow(x, 3) - 15 * pow(x, 2) + 36 * x - 14;
    }
}
