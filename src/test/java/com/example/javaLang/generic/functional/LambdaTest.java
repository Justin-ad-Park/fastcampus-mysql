package com.example.javaLang.generic.functional;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.stream.LongStream;

public class LambdaTest {

    @Test
    void oldFashionTest() {
        File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isHidden();
            }
        });

        Arrays.stream(hiddenFiles).forEach(System.out::println);
    }

    @Test
    void java8StyleTest() {
        File[] hiddenFiles = new File(".").listFiles(File::isHidden);

        Arrays.stream(hiddenFiles).forEach(System.out::println);
    }

    @Test
    void add1_000_000() {
        StopWatch sw = new StopWatch();

        sw.start();

        long sum = 0;
        for(int i = 0; i <= 1_000_000_000 ; i++)
            sum += i;

        sw.stop();

        System.out.println("Simple Add :  " + sum );
        System.out.println(sw.getTotalTimeSeconds());
    }

    @Test
    void add1_000_000_Lambda() {
        StopWatch sw = new StopWatch();

        sw.start();

        LongStream ls = LongStream.rangeClosed(0, 1_000_000_000);
        long sum = ls.parallel().sum();

        sw.stop();

        System.out.println("Lambda parallel Add : " + sum );
        System.out.println(sw.getTotalTimeSeconds());
    }

    @Test
    void add1_000_000_algorithm() {
        StopWatch sw = new StopWatch();

        sw.start();

        final Long maxValue = 1_000_000_000L;
        Long sum = (1 + maxValue) * (maxValue / 2);

        sw.stop();

        System.out.println("Algorithm Add : " + sum );
        System.out.println(sw.getTotalTimeMillis());
    }


}
