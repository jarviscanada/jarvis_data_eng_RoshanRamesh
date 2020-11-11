package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * https://www.notion.so/How-to-compare-two-maps-a749ef82725c444090782ba43e6b03b4
 */

public class CompareMaps {

    /**
     *Description: Compares two HashMaps to check if they are equal
     * Big O: O(n)
     * Justifications: Iteration has to be done through every key/value
     */

    public static <K,V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2){
        return m1.equals(m2);
    }

}
