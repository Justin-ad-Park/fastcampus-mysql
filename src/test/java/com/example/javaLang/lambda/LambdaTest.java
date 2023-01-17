package com.example.javaLang.lambda;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

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
}
