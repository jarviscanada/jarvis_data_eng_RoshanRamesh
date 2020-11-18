package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {

    @Test
    public void fib() {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fib(5));
    }

    @Test
    public void fibDyn() {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fibDyn(5));
    }
}