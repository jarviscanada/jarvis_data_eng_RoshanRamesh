package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Swap-two-numbers-e2026a6fac014ce488501c9af9704589
 */

public class SwapTwoNumbers {

    /**
     *Description: Swap two numbers using bitwise operator
     * Big O: O(1)
     * Justification: Simple bitwise operator, constant time.
     */

    public int[] bitwise(int[] input){
        if(input.length!=2){
            throw new IllegalArgumentException("Invalid inut size. Limit is 2");
        }
        input[0] = input[0] ^ input[1];
        input[1] = input[0] ^ input[1];
        input[0] = input[0] ^ input[1];
        return input;
    }

    /**
     * Description: Swap two numbers using arithmetic operations
     * Big O: O(1)
     * Justification: Simple arithmetic operations, constant time.
     */

    public int[] mathSwap(int[] input){
        if(input.length != 2){
            throw new IllegalArgumentException("Invalid inut size. Limit is 2");
        }
        input[0] = input[0] + input [1];
        input[1] = input[0] - input [1];
        input[0] = input[0] - input [1];
        return input;
    }

}
