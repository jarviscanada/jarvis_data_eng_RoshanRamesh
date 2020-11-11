package ca.jrvs.practice.codingChallenge;

import java.util.regex.Pattern;

/**
 * https://www.notion.so/Check-if-a-String-contains-only-digits-ba7cb898399b48e09efdce82f5305e1f
 */

public class StringContainsDigits {

    /**
     * Description: Check if a string contains only digits using ASCII
     * Big O: O(n)
     * Justification: Regular iteration through all characters in a string
     */
    public Boolean ascii(String str){
        if (str.length() == 0) {
            return false;
        }
        char[] arr = str.toCharArray();
        for(int i = 0; i < str.length(); i++){
            if(arr[i]<'0'||arr[i]>'9'){
                return false;
            }
        }
        return true;
    }

    /**
     * Desctiption: Determine if a string contains only digits using Java API
     * Big O: O(n)
     * Justifications: Every charcater of string is checked
     */

    public Boolean JavaApi(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Description: Find if string contains only digits using Regex
     * Big O: O(n)
     * Justification: Regex pattern matching checks every string character
     */

    public Boolean regex(String str){
        String regex = "[0-9]+";
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(str).matches();
    }

}
