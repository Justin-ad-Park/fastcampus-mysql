package com.example.javaLang.generic.functional.throwable.a3_optional;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Optional을 활용해 예외를
 * null을 반환하는 대신 Optional.empty()를 반환하게 되면
 * 조금 더 명시적으로 예외를 처리할 수 있다.
 */
public class OptionalTest {

    private Optional<String> safeReadString(Path path) {
        try {
            String content = Files.readString(path);
            return Optional.ofNullable(content);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Test
    void test() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .forEach(System.out::println);
    }

    @Test
    void test1() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .flatMap(Optional::stream)
                .forEach(System.out::println);
    }

    @Test
    void test2() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);
    }

    /** Optional을 활용한 파일 읽기 단점
     *  에러를 인지하지 못함
     *
     *  예외를 Option.empty()로 위장했을 뿐 예외가 발생했음을 여전히 숨기기 때문에
     *  이렇게 처리해도 되는 상황 외에는 예외를 숨기는 것은 좋지 않다.
      */

}
