package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringTest {

    @Test
    public void rotateStringTest() {
        RotateString rotateString = new RotateString();
        assertEquals(true, rotateString.rotateString("eabac", "abace"));
        assertEquals(false, rotateString.rotateString("abcde", "bdeca"));

    }
}