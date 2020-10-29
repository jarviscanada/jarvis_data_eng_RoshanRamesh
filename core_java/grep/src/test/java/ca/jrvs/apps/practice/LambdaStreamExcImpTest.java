package ca.jrvs.apps.practice;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.Arrays.stream;

public class LambdaStreamExcImpTest {

    //private LambdaStreamExcImp LambdaStreamExcImp;
    private ca.jrvs.apps.practice.LambdaStreamExcImp lambdaStreamExcImp;

    @org.junit.Before
    public void setUp() throws Exception {
        lambdaStreamExcImp = new LambdaStreamExcImp();
    }

    @org.junit.Test
    public void createStrStream() {
        System.out.println("createStrStream test");
        Stream<String> test = lambdaStreamExcImp.createStrStream("ro", "sh", "an");
        String str = test.collect(Collectors.joining(""));
        System.out.println(str);
    }

    private void assertEquals(String ouput_should_be_roshan, String roshan, String str) {
    }

    @org.junit.Test
    public void toUpperCase() {
        System.out.println("toUpperCase test");
        Stream<String> test = lambdaStreamExcImp.toUpperCase("ro", "sh", "an");
        String str = test.collect(Collectors.joining(""));
        System.out.println(str);
    }

    @org.junit.Test
    public void filter() {
        System.out.println("filter test");
        Stream<String> test = lambdaStreamExcImp.createStrStream("ro", "sh", "an");
        String pattern = "a";
        String str = lambdaStreamExcImp.filter(test, pattern).collect(Collectors.joining(""));
        System.out.println(str);
    }

    @org.junit.Test
    public void createIntStream() {
        System.out.println("createIntStream test");
        int[] arr = {1,2,4};
        IntStream test = lambdaStreamExcImp.createIntStream(arr);
        System.out.println(test.sum());
    }

    @org.junit.Test
    public void toList() {
        System.out.println("toList test");
        Stream<String> test = lambdaStreamExcImp.createStrStream("s", "w", "i", "m");
        //List<String> testList = Arrays.asList("s", "w", "i", "m");
        System.out.println(lambdaStreamExcImp.toList(test));
        //assertEquals("The expected outcome is abc", testList, lambdaStreamExcImp.toList(test));
    }

  //  @org.junit.Test
  //  public void testToList() {
  //  }

   // @org.junit.Test
   // public void testCreateIntStream() {
   // }

    @org.junit.Test
    public void squareRootIntStream() {
        System.out.println("squareRootIntStream test");
        int[] arr = {1,4,16};
        IntStream test = lambdaStreamExcImp.createIntStream(arr);
        DoubleStream test2 = lambdaStreamExcImp.squareRootIntStream(test);
        System.out.println(test2.sum());
    }

    @org.junit.Test
    public void getOdd() {
        System.out.println("getOdd test");
        int[] arr = {1,2,3,4,5};
        IntStream test = lambdaStreamExcImp.createIntStream(arr);
        System.out.println(lambdaStreamExcImp.getOdd(test).sum());
    }

    @org.junit.Test
    public void getLambdaPrinter() {
        System.out.println("getLambdaPrinter test");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("I:", ":Icecream");
        printer.accept("love");
        System.out.println(out.toString());
    }

    @org.junit.Test
    public void printMessages() {
        System.out.println("printMessage test");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String[] test = {"1", "3", "5"};
        Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("|", "|");
        lambdaStreamExcImp.printMessages(test, printer);
        System.out.println(out.toString());
    }

    @org.junit.Test
    public void printOdd() {
        System.out.println("printOdd test");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int[] test = {1,2,3};
        Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("|", "|");
        lambdaStreamExcImp.printOdd(Arrays.stream(test), printer);
        System.out.println(out.toString());
    }

    @org.junit.Test
    public void flatNestedInt() {
        System.out.println("flatNestedInt test");
        List<Integer> list1 = Arrays.asList(2, 2, 2);
        List<Integer> list2 = Arrays.asList(3, 3, 3);
        Stream<List<Integer>> testStream = Arrays.asList(list1, list2).stream();
        System.out.println(lambdaStreamExcImp.flatNestedInt(testStream).reduce(0, Integer::sum));
    }
}