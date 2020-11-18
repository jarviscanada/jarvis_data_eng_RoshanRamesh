package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Valid-Palindrome-b41087942a3d4d89914115de43df7e01
 */

public class ValidPalindrome {

    /**
     * Description: Returns true if a string is a valid palindrome
     * Big O: O(n)
     * justification: loops through half the length of string from front and back
     */

    public Boolean validPalindromeTwoPtr(String str){
        str = str.replaceAll("\\s","").toLowerCase();
        int front = 0;
        int back = str.length()-1;
        while (back > front){
            char frontchar = str.charAt(front++);
            char backchar = str.charAt(back--);
            if (frontchar != backchar){return false;}
        }
        return true;
    }

    /**
     * Description: Returns true if a string is a valid palindrome
     * Big O: O(n)
     * Justification: Recursion happens for each pair front/back
     */

    public Boolean validPalindromeRecursion(String str){
        str = str.replaceAll("\\s","").toLowerCase();
        return recursionPalindrome(str, 0, str.length()-1);
    }

    private Boolean recursionPalindrome (String text, int front, int back){
        if (front == back) {
            return true;
        }
        if ((text.charAt(front)) != (text.charAt(back))) {
            return false;
        }
        if (front < back + 1) {
            return recursionPalindrome(text, front + 1, back - 1);
        }

        return true;
    }
}
