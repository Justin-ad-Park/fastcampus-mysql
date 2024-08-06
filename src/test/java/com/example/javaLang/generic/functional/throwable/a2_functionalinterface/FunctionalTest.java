package com.example.javaLang.generic.functional.throwable.a2_functionalinterface;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 함수형 인터페이스를 활용한 파일 읽기 테스트
    람다식에서 try-catch 블럭을 코딩하지 않고, 파일을 읽어오는 함수형 인터페이스를 활용한 테스트
 */
public class FunctionalTest {

    @Test
    void test0() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map((ThrowingFunction<Path, Object>) path -> Files.readString(path))
                .filter(Objects::nonNull)
                .forEach(System.out::println);

    }

    ThrowingFunction<Path, String> readFileString = Files::readString;

    @Test
    void test() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(readFileString)
                .filter(Objects::nonNull)
                .forEach(System.out::println);

    }
}
