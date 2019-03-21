package org.donntu.ai.backend.component.genetic;

/**
 * @author Shilenko Alexander
 */
public interface FitnessFunction {
    int getArity();
    double getFunctionValue(StringBuffer chromosome);
}
