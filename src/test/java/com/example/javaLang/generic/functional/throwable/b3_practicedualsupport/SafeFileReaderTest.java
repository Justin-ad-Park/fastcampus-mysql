package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SafeFileReaderTest {

    @Test
    void test() {
        SafeFileReader reader = new SafeFileReader();

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        testResult(ResultFactories.createResultClass(), path1, path2, path3);

    }

    @Test
    void test2() {
        SafeFileReader reader = new SafeFileReader();

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/data1.txt");
        Path path3 = Paths.get("src/main/resources/data2.txt");

        testResult(ResultFactories.createResultRecord(), path1, path2, path3);

    }

    private  <R, E extends Throwable> void testResult(ResultFactory<R, E> factory, Path... paths) {
        SafeFileReader reader = new SafeFileReader();

        var result = Stream.of(paths)
                .map(path -> reader.read(path, factory))
                .collect(Collectors.groupingBy(ResultFactories::isSuccess, Collectors.toList()));

        System.out.println("== 성공 데이터 ==");
        result.getOrDefault(true, List.of()).forEach(System.out::println);

        System.out.println("== 실패 데이터 ==");
        result.getOrDefault(false, List.of()).forEach(System.out::println);


    }

}
