package org.example;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private static List<BigInteger> numbersList;

    public static void processFile(String filename) throws IOException {
        numbersList = Files.lines(Paths.get(filename))
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .filter(numberStr -> !numberStr.isEmpty())
                .map(BigInteger::new)
                .collect(Collectors.toList());
    }

    public static BigInteger _min() {
        return numbersList.stream().min(BigInteger::compareTo).orElse(BigInteger.ZERO);
    }

    public static BigInteger _max() {
        return numbersList.stream().max(BigInteger::compareTo).orElse(BigInteger.ZERO);
    }

    public static BigInteger _sum() {
        return numbersList.stream().reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static BigInteger _mult() {
        return numbersList.stream().reduce(BigInteger.ONE, BigInteger::multiply);
    }

    public static void main(String[] args) {
        try {
            processFile("numbers.txt");
            System.out.println("Min: " + _min());
            System.out.println("Max: " + _max());
            System.out.println("Sum: " + _sum());
            System.out.println("Mult: " + _mult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
