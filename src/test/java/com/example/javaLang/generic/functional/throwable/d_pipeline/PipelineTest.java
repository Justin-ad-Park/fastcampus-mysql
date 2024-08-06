package com.example.javaLang.generic.functional.throwable.d_pipeline;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class PipelineTest {

    @Test
    void test() {
        var path = Path.of("src/main/resources/data.txt");

        //Try<Path, String> result = Try.<Path, String>of(Files::readString);

        Optional<String> content = Try.<Path, String>of(Files::readString)
                .success(String::toUpperCase)
                .failure(e -> null)
                .apply(path);

        System.out.println(content.orElse("Error"));
    }

    private Function<Path, Optional<String>> fileLoader =
            Try.<Path, String>of(Files::readString)
                    .success(String::toUpperCase)
                    .failure(e -> String.format("Error: %s", e.getMessage()));

    @Test
    void test1() {

        Path path1 = Paths.get("src/main/resources/data.txt");
        Path path2 = Paths.get("src/main/resources/dataError.txt");
        Path path3 = Paths.get("src/main/resources/data1.txt");


        var fileContents = Stream.of(path1, path2, path3)
                .map(fileLoader)
                .flatMap(Optional::stream)
                .toList();

        fileContents.forEach(System.out::println);

    }
}
