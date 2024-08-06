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

public class FileIOv3Test {

    /**
     * 파일을 안전하게 읽고 결과를 반환하는 제네릭 메서드.
     *
     * @param path    읽을 파일의 경로
     * @param factory 결과를 생성하는 팩토리
     * @param <R>     결과 타입
     * @return 파일 내용을 포함하는 성공 또는 실패 결과
     */
    private <R, E extends Throwable> R safeReadString(Path path, ResultFactory<R, E> factory) {
        try {
            // 파일의 내용을 읽고 성공 결과를 반환
            return factory.success(Files.readString(path));
        } catch (IOException e) {
            // 파일 읽기에 실패하면 실패 결과를 반환
            return factory.failure((E) e);
        } catch (Exception e) {
            // 그 외의 예외도 실패 결과를 반환하도록 수정
            return factory.failure((E) e);
        }

    }

    @Test
    void test() {
        // 테스트할 파일 경로 설정
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Path.of("src/main/resources/data1.txt");
        Path path3 = Path.of("src/main/resources/data2.txt");

        // RecordResult를 사용한
        // 테스트
        System.out.println("RecordResult 테스트:");
        testResult(createClassResultFactory(), path1, path2, path3);
    }

    @Test
    void test2() {
        // 테스트할 파일 경로 설정
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Path.of("src/main/resources/data1.txt");
        Path path3 = Path.of("src/main/resources/data2.txt");

        // RecordResult를 사용한
        // 테스트
        System.out.println("RecordResult 테스트:");
        testResult(createRecordResultFactory(), path1, path2, path3);
    }


    /**
     * 파일 경로 목록을 처리하여 결과를 그룹화하고 출력하는 메서드.
     *
     * @param factory 결과를 생성하는 팩토리
     * @param paths   파일 경로들
     * @param <R>     결과 타입
     */
    private <R> void testResult(ResultFactory<R, IOException> factory, Path... paths) {
        // 파일 경로 스트림을 생성하고 결과를 수집하여 성공과 실패로 그룹화
        Map<Boolean, List<R>> result = Stream.of(paths)
                .map(path -> safeReadString(path, factory))
                .collect(Collectors.groupingBy(this::isSuccess, Collectors.toList()));

        // 성공 결과 처리
        System.out.println("성공:");
        result.getOrDefault(true, List.of()).forEach(System.out::println);

        // 실패 결과 처리
        System.out.println("실패:");
        result.getOrDefault(false, List.of()).forEach(System.out::println);
    }

    /**
     * 주어진 결과 객체가 성공인지 확인하는 메서드.
     *
     * @param r 결과 객체
     * @param <R> 결과 타입
     * @return 성공 여부
     */
    private <R> boolean isSuccess(R r) {
        if (r instanceof ClassResult) {
            return ((ClassResult<?>) r).isSuccess();
        } else if (r instanceof ResultRecord) {
            return ((ResultRecord<?>) r).isSuccess();
        }
        throw new IllegalArgumentException("Unknown result type: " + r.getClass());
    }

    // ClassResult를 위한 팩토리 생성 메서드
    private <E extends Throwable> @NotNull ResultFactory<ClassResult<E>, E> createClassResultFactory() {
        return createResultFactory(ClassResult::success, ClassResult::failure);
    }

    // RecordResult를 위한 팩토리 생성 메서드
    private <E extends Throwable> @NotNull ResultFactory<ResultRecord<E>, E> createRecordResultFactory() {
        return createResultFactory(ResultRecord::success, ResultRecord::failure);
    }

    // 제네릭 팩토리 생성 메서드
    private <R, E extends Throwable> @NotNull ResultFactory<R, E> createResultFactory(
            ResultSuccessFactory<R> successFactory,
            ResultFailureFactory<R, E> failureFactory) {
        return new ResultFactory<>() {
            @Override
            public R success(String value) {
                return successFactory.create(value);
            }

            @Override
            public R failure(E e) {
                return failureFactory.create(e);
            }
        };
    }

    // 팩토리 인터페이스: 성공과 실패를 처리하는 메서드를 포함
    private interface ResultFactory<R, E extends Throwable> {
        R success(String value);
        R failure(E e);
    }

    // 성공 결과를 생성하는 인터페이스
    private interface ResultSuccessFactory<R> {
        R create(String value);
    }

    // 실패 결과를 생성하는 인터페이스
    private interface ResultFailureFactory<R, E extends Throwable> {
        R create(E e);
    }
}