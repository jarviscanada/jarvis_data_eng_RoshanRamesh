package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareMapsTest {

    @Test
    public void compareMaps() {
        CompareMaps compareMaps = new CompareMaps();
        Map<Integer, String> map1 = new HashMap<Integer, String>();
        map1.put(1, "B");

        Map<Integer, String> map2 = new HashMap<Integer, String>();
        map2.put(1, "A");

        System.out.println(CompareMaps.compareMaps(map1, map2));
    }


}