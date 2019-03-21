package org.donntu.ai.backend.utils;

import java.math.BigInteger;

public class NumericUtil {
    public static StringBuffer doubleToBinaryString(Double d) {
        return new StringBuffer(Long.toBinaryString(Double.doubleToRawLongBits(d)));
    }

    public static Double binaryStringToDouble(StringBuffer s) {
        return Double.longBitsToDouble(new BigInteger(s.toString(), 2).longValue());
    }
}
