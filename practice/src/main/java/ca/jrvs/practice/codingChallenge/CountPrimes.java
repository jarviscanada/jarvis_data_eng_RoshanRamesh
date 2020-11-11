package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Count-Primes-c74c7961edc04c41bc597849ab4ff3ff
 */

public class CountPrimes{

    /**
     * Description: Counts prime numbers between 0 and input argument
     * Big O: O(n^2)
     * Justification: Consists of nested loops
     */

    public int countPrimes(int num){
    if (num < 0) {
        throw new IllegalArgumentException("Invalid input- negative number");
    }
    if (num == 1 || num == 2) {
        return 0;
    }
    int count = 0;
    for (int i = 3; i < num; i++) {
        boolean prime = true;
        for (int j = 2; j < i/2; j++) {
            if (i%j == 0) {
                prime = false;
                break;
            }
        }
        if (prime) {
            count++;
        }
    }
    return count;
}

}
