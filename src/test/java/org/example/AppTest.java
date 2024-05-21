package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class AppTest {

    @BeforeAll
    public static void setup() throws IOException {
        createFile("test1.txt", "1 2 3 4 5");
        createFile("test2.txt", "-1 -2 -3 -4 -5");
        createLargeFile("test10000.txt", 10000);
        createLargeFile("test100000.txt", 100000);
        createLargeFile("test1000000.txt", 1000000);
    }

    @Test
    public void testMin() throws IOException {
        App app1 = new App();
        app1.processFile("test1.txt");
        assertEquals(new BigInteger("1"), app1._min());

        app1.processFile("test2.txt");
        assertEquals(new BigInteger("-5"), app1._min());

        app1.processFile("test10000.txt");
        assertEquals(new BigInteger("1"), app1._min());

        app1.processFile("test100000.txt");
        assertEquals(new BigInteger("1"), app1._min());

        app1.processFile("test1000000.txt");
        assertEquals(new BigInteger("1"), app1._min());
    }

    @Test
    public void testMax() throws IOException {
        App app2 = new App();
        app2.processFile("test1.txt");
        assertEquals(new BigInteger("5"), app2._max());

        app2.processFile("test2.txt");
        assertEquals(new BigInteger("-1"), app2._max());

        app2.processFile("test10000.txt");
        assertEquals(new BigInteger("10000"), app2._max());

        app2.processFile("test100000.txt");
        assertEquals(new BigInteger("100000"), app2._max());

        app2.processFile("test1000000.txt");
        assertEquals(new BigInteger("1000000"), app2._max());
    }

    @Test
    public void testSum() throws IOException {
        App app = new App();
        app.processFile("test1.txt");
        assertEquals(new BigInteger("15"), app._sum());

        app.processFile("test2.txt");
        assertEquals(new BigInteger("-15"), app._sum());

        app.processFile("test10000.txt");
        assertEquals(new BigInteger("50005000"), app._sum());

        app.processFile("test100000.txt");
        assertEquals(new BigInteger("5000050000"), app._sum());

        app.processFile("test1000000.txt");
        assertEquals(new BigInteger("500000500000"), app._sum());
    }

    @Test
    public void testMult() throws IOException {
        App app4 = new App();
        app4.processFile("test1.txt");
        assertEquals(new BigInteger("120"), app4._mult());

        app4.processFile("test2.txt");
        assertEquals(new BigInteger("-120"), app4._mult());




    }

    @Test
    public void testPerformance() throws IOException {
        int[] sizes = {1000, 10000, 100000, 1000000};
        int repetitions = 5;

        for (int size : sizes) {
            long totalTime = 0;
            for (int i = 0; i < repetitions; i++) {
                createLargeFile("perfTest.txt", size);
                App app = new App();

                long startTime = System.nanoTime();
                app.processFile("perfTest.txt");

                app._max();

                long endTime = System.nanoTime();

                totalTime += (endTime - startTime);
            }
            long averageTime = totalTime / repetitions;
            System.out.printf("Average time for processing file of size %d: %d ms%n", size, TimeUnit.NANOSECONDS.toMillis(averageTime));
        }
    }

    private static void createFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        }
    }

    private static void createLargeFile(String filename, int size) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= size; i++) {
                sb.append(i).append(" ");
                if (i % 1000 == 0) {
                    writer.write(sb.toString());
                    sb.setLength(0);
                }
            }
            writer.write(sb.toString());
        }
    }
}
