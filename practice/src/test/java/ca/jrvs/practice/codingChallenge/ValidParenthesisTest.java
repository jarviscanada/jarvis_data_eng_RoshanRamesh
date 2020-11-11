package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesisTest {

    @Test
    public void validParenthesis(){
        ValidParenthesis validParenthesis = new ValidParenthesis();
        System.out.println(validParenthesis.validParenthesis("[]"));
        System.out.println(validParenthesis.validParenthesis("[{()]"));
    }

}