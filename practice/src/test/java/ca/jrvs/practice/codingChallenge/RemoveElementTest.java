package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveElementTest {

    @Test
    public void removeElementTest(){
        RemoveElement removeElement = new RemoveElement();
        int[] in = new int[]{1,2,3,4,5};
        System.out.println(removeElement.removeElement(in,2));
    }

}