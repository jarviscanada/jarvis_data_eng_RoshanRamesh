package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {

    @Test
    public void validPalindrome() {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        assertEquals(true, validPalindrome.validPalindromeTwoPtr("race car"));
    }

    @Test
    public void validPalindromeRec(){
        ValidPalindrome validPalindrome = new ValidPalindrome();
        assertEquals(true, validPalindrome.validPalindromeRecursion("aba"));
    }

}