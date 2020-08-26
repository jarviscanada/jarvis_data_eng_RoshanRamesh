package ca.jrvs.apps.practice;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Arrays.stream;

public class LambdaStreamExcImp implements LambdaStreamExc {
    public Double squareRootIntStream;

    @Override
    public Stream<String> createStrStream(String... strings) {
        return stream(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return stream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string -> !string.contains(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return stream(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.range(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(x -> x % 2 == 1);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return (x -> System.out.println(prefix + x + suffix));
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        stream(messages).forEach(printer::accept);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        printMessages(getOdd(intStream).mapToObj(String::valueOf).toArray(String[]::new), printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(list -> list.stream().map(x -> x*x));
    }
}
