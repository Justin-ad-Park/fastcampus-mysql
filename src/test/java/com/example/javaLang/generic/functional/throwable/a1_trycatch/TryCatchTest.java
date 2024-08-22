package com.example.javaLang.generic.functional.throwable.a1_trycatch;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 함수형 프로그래밍에서 예외 상황이 발생하는 경우의 처리
 */
public class TryCatchTest {

    /** 스트림에서도 예외는 try-catch **/
//    void testPrintStackTrace_err()  throws IOException {
//        Path path1 = Path.of("src/main/resources/data.txt");
//        Path path2 = Paths.get("src/main/resources/dataError.txt");
//        Path path3 = Path.of("src/main/resources/data1.txt");
//
//        Stream.of(path1, path2, path3)
//                .map(path -> {
//                    return Files.readString(path);    /** IOException을 처리하지 않아 컴파일 에러 발생 **/
//                })
//                .forEach(System.out::println);
//    }

    @Test
    void testPrintStackTrace() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(path -> {
                    try{
                        return Files.readString(path);
                    } catch (IOException e) {
                        e.printStackTrace();    //예외 발생 시 프로세스 중단됨
                    }
                    return "";
                })
                .forEach(System.out::println);
    }

    @Test
    void testThrow() {
        throwMethod();
    }

    private static void throwMethod() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(path -> {
                    try{
                        return Files.readString(path);
                    } catch (IOException e) {
                        /** 예외 발생 시 throw로 호출한 측에서 예외처리 가능하지만,
                         * 예외가 발생한 시점부터 스트림 처리는 여전히 중단됨 **/
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    @Test
    void testCatch() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(path -> {
                    try{
                        return Files.readString(path);
                    } catch (IOException e) {
                        /**
                         * 예외를 무시하면 스트림 처리를 끝까지 진행할 수 있지만,
                         * 예외를 처리하지 않는 것은 문제점이 숨겨져 더 큰 문제가 될 수 있음
                         */
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }

    @Test
    void testExtractMethod() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(getPathString())   //복잡한 try-catch 구문을 분리
                .forEach(System.out::println);
    }

    /**
     * 스트림의 map()은 Function을 받으며, 스트림을 인자로 받고, 리턴값이 있으면 됨
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     * @return
     */

    // 함수형인터페이스인 Function에 해당하는 람다 메서드를 참조로 제공
    private static @NotNull Function<Path, String> getPathString() {
        return path -> {
            try {
                return Files.readString(path);
            } catch (IOException e) {
                return null;
            }
        };
    }

    @Test
    void testExtractMethodAndFilter() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Stream.of(path1, path2, path3)
                .map(getPathString())
                .filter(Objects::nonNull)   //필터를 사용해서 null을 제외하고 오류가 없는 정상 데이터만 처리
                .forEach(System.out::println);
    }

}
