package ca.jrvs.practice.codingChallenge;

/**
 *  https://www.notion.so/Fibonacci-Number-Climbing-Stairs-b68801a33e9d4df09af74bc1fb83fd5c
 */

public class Fibonacci {

    /**
     * Returns Fibonacci number of a given integer using Recursion
     * Time complexity: O(2^N) - exponential time and slowest method.
     * Justification: Number of operations for recursive level, grows exponentially as integer approaches N
     */

    public int fib(int N) {
        if (N <= 1)
            return N;
        else
            return fib(N - 1) + fib(N - 2);
    }

    /**
     * Returns Fibonnaci number of a given integer using dynamic programming
     * Timr complexity: O(N)
     * Justification: Each number, starting at 2 up to and including N, is visited, computed and then stored for O(1) access later on.
     */

    public int fibDyn(int N) {
        if (N <= 1)
            return N;
        else {
            int[] memo = new int[N + 1];
            memo[0] = 0;
            memo[1] = 1;
            for (int i = 2; i <= N; i++) {
                memo[i] = memo[i - 1] + memo[i - 2];
            }
            return memo[N];
        }
    }
}
