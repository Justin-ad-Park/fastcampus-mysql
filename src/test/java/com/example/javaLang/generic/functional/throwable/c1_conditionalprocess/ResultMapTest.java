package com.example.javaLang.generic.functional.throwable.c1_conditionalprocess;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

public class ResultMapTest {

    public Result<String, IOException> safeReadString(Path path) {
        try {
            return Result.success(Files.readString(path));
        } catch (IOException e) {
            return Result.failure(e);
        }
    }

    @Test
    void test() {

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .filter(r-> r.isSuccess())          //성공만 처리
                .map(r->r.value().toUpperCase())
                .forEach(System.out::println);
    }


    @Test
    void test1() {

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .map(result -> result.mapSuccess(String::toUpperCase))  //성공 mapper로 처리
                .forEach(System.out::println);
    }

    @Test
    void test2() {

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .map(r ->
                        r.mapSuccessFailure(
                                String::toUpperCase,    //성공 mapper -> 대문자로 변경
                                e -> "IO Error: " + e.getMessage()  //실패 mapper -> 에러 메시지 출력
                        )
                )//success, failure 동시에 처리
                .forEach(System.out::println);

    }

    @Test
    void test3() {

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .map(successFailureMapper)   // 성공과 실패를 각각 처리해서 결과를 리턴하는
                .forEach(System.out::println);

    }

    Function<String, String> successMapper = String::toUpperCase;

    Function<IOException, String> failureMapper =
            e -> "IO Error: " + e.getMessage();

    Function<Result<String, IOException>, String> successFailureMapper =
        r ->
                r.mapSuccessFailure(
                        successMapper,
                        failureMapper
                );

}
