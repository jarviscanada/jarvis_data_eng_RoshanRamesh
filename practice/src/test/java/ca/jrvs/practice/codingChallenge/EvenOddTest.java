package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvenOddTest {

    @Test
    public void evenOddMod(){
        EvenOdd evenOdd = new EvenOdd();
        System.out.println(evenOdd.evenOddMod(5));
    }

    @Test
    public void evenOddbit(){
        EvenOdd evenOdd = new EvenOdd();
        System.out.println(evenOdd.evenOddbit(6));
    }


}