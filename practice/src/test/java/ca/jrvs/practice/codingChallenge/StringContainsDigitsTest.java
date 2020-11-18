package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringContainsDigitsTest {

    @Test
    public void asciiTest(){
        StringContainsDigits s = new StringContainsDigits();
        assertEquals(true, s.ascii("1234"));
        assertEquals(false, s.ascii("1234, 5678"));
    }

    @Test
    public void javaApi(){
        StringContainsDigits s = new StringContainsDigits();
        assertEquals(true, s.JavaApi("1234"));
        assertEquals(false, s.JavaApi("1234,5678"));
    }

    @Test
    public void regexTest(){
        StringContainsDigits s = new StringContainsDigits();
        assertEquals(true, s.regex("1234"));
        assertEquals(false, s.regex("1234, 5678"));
    }

}