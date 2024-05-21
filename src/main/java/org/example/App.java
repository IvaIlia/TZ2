package org.example;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private List<BigInteger> numbersList;

    public void processFile(String filename) throws IOException {
        numbersList = Files.lines(Paths.get(filename))
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .filter(numberStr -> !numberStr.isEmpty())
                .map(BigInteger::new)
                .collect(Collectors.toList());
    }

    public BigInteger _min() {
        return numbersList.stream().min(BigInteger::compareTo).orElse(BigInteger.ZERO);
    }

    public BigInteger _max() {
        return numbersList.stream().max(BigInteger::compareTo).orElse(BigInteger.ZERO);
    }

    public BigInteger _sum() {
        return numbersList.stream().reduce(BigInteger.ZERO, BigInteger::add);
    }

    public BigInteger _mult() {
        return numbersList.stream().reduce(BigInteger.ONE, BigInteger::multiply);
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.processFile("numbers.txt");
            System.out.println("Min: " + app._min());
            System.out.println("Max: " + app._max());
            System.out.println("Sum: " + app._sum());
            System.out.println("Mult: " + app._mult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
