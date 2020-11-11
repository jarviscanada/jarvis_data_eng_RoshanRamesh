package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://www.notion.so/Duplicate-Characters-c5dab6018be0407a8503f2b489adc986
 */

public class DuplicateCharacters {

    /**
     * Description: Returns an array of duplicate characters in a string
     * Big O: O(n)
     * Justification: Iterates through each character
     * @return
     */

    public String[] findDuplicate(String s){
        Set<Character> set = new HashSet<>();
        List<String> result = new ArrayList<>();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                if(set.contains(s.charAt(i))){
                    result.add(Character.toString(s.charAt(i)));
                }else{
                    set.add(s.charAt(i));
                }
            }
        }
        String[] arr=new String[result.size()];
        return result.toArray(arr);
    }

}
