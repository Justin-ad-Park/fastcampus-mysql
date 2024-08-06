package com.example.javaLang.generic.functional.throwable.b2_stepbystep;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileIOTest {

    ClassResult<IOException> safeReadString(Path path) {
        try {
            return ClassResult.success(Files.readString(path));
        } catch (IOException e) {
            return ClassResult.failure(e);
        }
    }

    @Test
    void test() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Path.of("src/main/resources/data1.txt");

        Map<Boolean, List<ClassResult<IOException>>> result = Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .collect(
                        Collectors.groupingBy(r->r.isSuccess(), Collectors.toList())
                );

        //성공만 처리
        result.get(true).stream()
                .forEach(System.out::println);

        //실패만 처리
        result.get(false).stream()
                .forEach(System.out::println);

    }

    /**
     * 스트림 내에서 성공과 예외를 모두 처리할 수 있는 로직을 구현했다.
     *
     * 다만, 스트림 밖에서 성공과 실패를 처리해서
     * 스트림이 가지는 여러 장점들(병렬처리, 간결함, 가독성)이 잘 활용되지 못했다.
     */
}
