package com.example.javaLang.generic.streamtest.files;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class FileStreamTest {

    @Test
    void deleteNoAccessFile() {
        var path = Paths.get(getUserHome(), "/Downloads");

        // 2년의 FileTime을 생성
        FileTime noAccessTime = FileTime.from(Instant.now().minus(Duration.ofDays(365 * 2)));

        // matcher를 이용한 파일 스트림 생성
        try (var stream = Files.find(path, Integer.MAX_VALUE, longTimeNoAccessMatcher(noAccessTime))) {
            stream
                    .filter(getFilter())      // 추가적인 필터가 필요한 경우
                    .limit(2)           // 과도한 처리를 제한하기 위한 수량 한정
                    .peek(System.out::println)  // 대상 파일 로그 출력
                    .forEach(FileStreamTest::deleteFile);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * try-with-resource 블록을 사용해 자원 반환을 보장
     *
     * Stream<T> 타입은 BaseStream을 통해 java.io.AutoCloseable을 구현한다.
     *
     *
     * Files.find
     * >> (선언부)
     *  public static Stream<Path> find(Path start,
     *    >> (선언부)
     *      public interface Stream<T> extends BaseStream<T, Stream<T>> {
     *        >>
     *          public interface BaseStream<T, S extends BaseStream<T, S>>
     *            extends AutoCloseable {
     */


    // BiPredicate 정의: 지정한 fileTime 동안 접근되지 않은 파일 찾기
    BiPredicate<Path, BasicFileAttributes> longTimeNoAccessMatcher(FileTime fileTime) {
        return (p, attr) -> attr.lastAccessTime().compareTo(fileTime) < 0;
    }

    //추가로 필요한 필터가 있으면 적절한 이름과 로직으로 변경 후 사용
    private static @NotNull Predicate<Path> getFilter() {
        return f -> true;
        //return f -> f.getFileName().toString().contains("Demons");
    }

    // 실제 테스트에서는 delete 로직을 사용할 것
    private static void deleteFile(Path entry) {
        System.out.println("Delete file : " + entry);

//        try {
//            Files.delete(entry);
//        } catch (IOException e) {
//            System.err.println("Failed to delete: " + entry + " due to " + e.getMessage());
//        }
    }


    @Test
    void filesWalkTest() {
        var path = Paths.get("/Users/justinpark/Downloads");

//        try {
//            // 파일 권한 설정
//            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-----");
//            Files.setPosixFilePermissions(path, permissions);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try (var stream = Files.walk(path)) {
            stream.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String getUserHome() {
        // 현재 사용자의 홈 디렉토리를 가져옵니다.
        String userHome = System.getProperty("user.home");

        return userHome;
    }

    @Test
    void filesWalkTest2() {
        var path = Paths.get(getUserHome(), "/Downloads");

        try (var stream = Files.walk(path)) {
            stream.map(Path::toFile)
                    .filter(Predicate.not(File::isFile))    //파일이 아닌 즉, 디렉터리
                    .sorted()
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
