package com.example.javaLang.generic.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HotToMoveFunctionalProgramTest {

    private String processFile() throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        return br.readLine();
    }


    @Test
    void ProcessFileTest() throws IOException {
        String output = processFile();

        System.out.println(output);

        Assertions.assertEquals("test", output);
    }


    /**
     * 함수형 인터페이스를 정의해 메서드 전달을 할 수 있도록 한다.
     */
    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    private String processFile(BufferedReaderProcessor p) throws IOException {
        BufferedReader br =
                new BufferedReader(new FileReader("src/main/resources/data.txt"));
        return p.process(br);
    }

    @Test
    void LambdaTest() throws IOException {
        String output = processFile((BufferedReader b) -> b.readLine());

        System.out.println(output);
        Assertions.assertEquals("test", output);


        String secondsLine = processFile( (BufferedReader b) -> {
            b.readLine();
            return b.readLine();});

        System.out.println(secondsLine);

        Assertions.assertEquals("abcdefg", secondsLine);
    }


}
