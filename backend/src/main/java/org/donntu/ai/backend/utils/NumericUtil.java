package org.donntu.ai.backend.utils;

public class NumericUtil {

    public static Double binaryStringToDouble(String s, double lowerInterval, double upperInterval) {
        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            sum += Character.getNumericValue(chars[i]) * Math.pow(2, chars.length - i - 1);
        }

        return lowerInterval + sum * ((upperInterval - lowerInterval) / (Math.pow(2, chars.length) - 1));
    }

    public static int getNearestMaxTwoPower(int number) {
        int pow = 0;
        while (number > 0){
            number >>= 1;
            pow++;
        }
        return pow;
    }
}
