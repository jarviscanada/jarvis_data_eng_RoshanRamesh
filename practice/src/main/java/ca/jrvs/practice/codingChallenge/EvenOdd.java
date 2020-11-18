package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Sample-Check-if-a-number-is-even-or-odd-5af19d6f48aa460589b0a7d47ea7495c
 */

public class EvenOdd {

    /**
     * Description: Determining if given number is even/odd
     * Big O: O(1)
     * Justification: single module operation. constant time.
     */

    public String evenOddMod(int i) {
        if(i%2 == 0){return "even";}
        else {return "odd";}
    }

    /**
     * Decription: Even odd using bitwise operator i.e., As we know bitwise XOR Operation of the Number by 1 increment the value of the number by 1 if the number is even otherwise it decrements the value of the number by 1 if the value is odd.
     * Big O: O(1)
     * Justiication: Simple XOR operation
     */

    public String evenOddbit(int i){
        if((i^1)==(i+1)){return "even";}
        else {return "odd";}
    }

}
