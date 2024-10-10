package com.example.javaLang.generic.functional.throwable.b2_stepbystep;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 두 가지 이상의 다른 클래스로 처리가 필요한 경우에
 * 제네릭과 함수형 인터페이스, 인터페이스를 활용해
 * 중복 로직을 제거하고, 객체지향적(SOLID)인 설계 구조를 만들 수 있다.
 *
 * S : 단일책임의 원칙
 * O : 개방폐쇄의 원칙
 * L : 리스코프 치환의 원칙 - 상위형을 하위형으로 치환 가능
 * I : 인터페이스 분리의 원칙
 * D : 의존성 역전의 원칙 - 의존성 주입
 */
public class FileIOv2Test {

    @Test
    void test() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Path.of("src/main/resources/data1.txt");
        Path path3 = Path.of("src/main/resources/data2.txt");

        // RecordResult 테스트
        testResult(path1, path2, path3, ResultRecord::success, ResultRecord::failure);
    }

    @Test
    void test2() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Path.of("src/main/resources/data1.txt");
        Path path3 = Path.of("src/main/resources/data2.txt");

        // RecordResult 테스트
        testResult(path1, path2, path3, ClassResult::success, ClassResult::failure);
    }

    // 제네릭 메서드로 변환
    private <R> R safeReadString(Path path, ResultFactory<R> factory) {
        try {
            return factory.success(Files.readString(path));
        } catch (IOException e) {
            return factory.failure(e);
        }
    }

    private <R> void testResult(Path path1, Path path2, Path path3,
                                ResultSuccessFactory<R> successFactory,
                                ResultFailureFactory<R> failureFactory) {

        Map<Boolean, List<R>> result = Stream.of(path1, path2, path3)
                .map(path -> safeReadString(path, createResultInstance(successFactory, failureFactory)))
                .collect(Collectors.groupingBy(r -> {
                    if (r instanceof ResultRecord) {
                        return ((ResultRecord<?>) r).isSuccess();
                    } else if (r instanceof ClassResult) {
                        return ((ClassResult<?>) r).isSuccess();
                    }
                    return false;
                }, Collectors.toList()));

        // 성공만 처리
        result.get(true).forEach(System.out::println);

        // 실패만 처리
        result.get(false).forEach(System.out::println);
    }

    private <R> @NotNull ResultFactory<R> createResultInstance(
            ResultSuccessFactory<R> successFactory,
            ResultFailureFactory<R> failureFactory) {
        return new ResultFactory<R>() {
            @Override
            public R success(String value) {
                return successFactory.create(value);
            }

            @Override
            public R failure(IOException e) {
                return failureFactory.create(e);
            }
        };
    }

    // 팩토리 인터페이스
    private interface ResultFactory<R> {
        R success(String value);
        R failure(IOException e);
    }

    // 성공 팩토리 인터페이스
    @FunctionalInterface
    private interface ResultSuccessFactory<R> {
        R create(String value);
    }

    // 실패 팩토리 인터페이스
    @FunctionalInterface
    private interface ResultFailureFactory<R> {
        R create(IOException e);
    }
}