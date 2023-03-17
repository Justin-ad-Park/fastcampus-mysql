package com.example.javaLang.generic.lambdapattern;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPattern {

    @Test
    void ReadFileOneLineTest() throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        String contents = br.readLine();

        System.out.println("한줄 : " + contents);
    }

    @Test
    void ReadFileTwoLinesTest() throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        String contents = br.readLine();
        contents += br.readLine();

        System.out.println("두줄 : " + contents);
    }

    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    private String processFile(BufferedReaderProcessor p) throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        return p.process(br);
    }

    BufferedReaderProcessor readOneLine = (BufferedReader b) -> b.readLine();
    BufferedReaderProcessor readTwoLines = (BufferedReader b) -> b.readLine() + b.readLine();

    @Test
    void LambdaTest() throws IOException {
        String result = processFile(readOneLine);
        System.out.println("한줄 : " + result);

        result = processFile(readTwoLines);
        System.out.println("두줄 : " + result);
    }


}
