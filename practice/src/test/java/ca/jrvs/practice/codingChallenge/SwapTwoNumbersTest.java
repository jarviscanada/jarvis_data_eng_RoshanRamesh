package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTwoNumbersTest {

    private SwapTwoNumbers swap;
    private int[] input;

    @Before
    public void setup() {
        swap = new SwapTwoNumbers();
        input = new int[]{1, 2};
    }

    @Test
    public void bitwise(){
        int[] output = swap.bitwise(input);
        assertEquals(2, output[0]);
        assertEquals(1, output[1]);
    }

    @Test
    public void mathSwap(){
        int[] output = swap.mathSwap(input);
        assertEquals(2, output[0]);
        assertEquals(1, output[1]);
    }

}