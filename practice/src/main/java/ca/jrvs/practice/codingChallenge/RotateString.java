package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Rotate-String-f977736cd7184b0f95fd5343acf4a297
 */

public class RotateString {

    /**
     * Description: Return true if a string is rotated version of the other
     * Big O: O(n^2)
     * Justification: Checks a string of length 2n if length is n
     */

    public boolean rotateString(String A, String B) {
        return A.length() == B.length() && (A + A).contains(B);
    }
}
