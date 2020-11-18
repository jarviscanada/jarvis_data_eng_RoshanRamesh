package ca.jrvs.practice.codingChallenge;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

/**
 * https://www.notion.so/Valid-Parentheses-e4bcbc9b9afc434fa64dc5d535314cd2
 */

public class ValidParenthesis {

    public boolean validParenthesis(String str){
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        map.put(']', '[');
        map.put('}', '{');
        map.put(')', '(');

        Stack<Character> stack = new Stack<Character>();

        for(int i=0; i<str.length(); i++){
            char k = str.charAt(i);
            if(map.containsKey(k)){
                try{
                    char top = stack.pop();
                    if (top != map.get(k)){
                        return false;
                    }
                } catch (EmptyStackException ex){
                    return false;
                }
            } else {
                stack.push(k);
            }
        }
        return stack.isEmpty();
    }

}
