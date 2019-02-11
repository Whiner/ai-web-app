package org.donntu.ai.backend.utils;

import java.util.HashSet;
import java.util.Set;

public class SetsUtils {
    public static <T> Set<T> iterableToSet(Iterable<T> iterable) {
        Set<T> set = new HashSet<>();
        iterable.forEach(set::add);
        return set;
    }
}
