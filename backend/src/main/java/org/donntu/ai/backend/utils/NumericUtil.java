package org.donntu.ai.backend.utils;

public class NumericUtil {
    public static StringBuffer doubleToBinaryString(Double d) {
        return new StringBuffer(Long.toBinaryString(Double.doubleToRawLongBits(d)));
    }

    public static Double binaryStringToDouble(String s, double a, double b) {
        //return Double.longBitsToDouble(new BigInteger(s.toString(), 2).longValue());

        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            sum += Character.getNumericValue(chars[i]) * Math.pow(2, chars.length - i - 1);
        }

        return a + sum * ((b - a) / (Math.pow(2, chars.length) - 1));
    }

    public static int getNearestTwoPower(int number) {
        int pow = 0;
        while (number > 0){
            number >>= 1;
            pow++;
        }
        return pow;
    }
}
