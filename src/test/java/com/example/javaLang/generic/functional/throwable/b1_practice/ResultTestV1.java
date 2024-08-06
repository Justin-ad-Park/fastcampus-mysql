package com.example.javaLang.generic.functional.throwable.b1_practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultTestV1 {

    ResultRecord<IOException> safeReadString(Path path) {
        try {
            return ResultRecord.success(Files.readString(path));    //성공 시 ResultRecord.success()로 결과 반환
        } catch (IOException e) {
            return ResultRecord.failure(e); // 예외 발생 시 ResultRecord.failure()로 결과 반환
        }
    }

    @Test
    void test() {
        Path path1 = Path.of("src/main/resources/data.txt");
        Path path2 = Path.of("src/main/resources/data1.txt");
        Path path3 = Path.of("src/main/resources/data2.txt");

        Map<Boolean, List<ResultRecord<IOException>>> result = Stream.of(path1, path2, path3)
                .map(this::safeReadString)
                .collect(Collectors.groupingBy(r->r.isSuccess(), Collectors.toList())   //성공, 실패로 결과 데이터를 그룹화
                );

        // 성공 결과 데이터 처리
        result.get(true).forEach(System.out::println);

        // 실패 결과 데이터 처리
        result.get(false).forEach(System.out::println);
    }

    /**
     * 성공과 실패를 모두 ResultRecord 객체에 구분해서 받으면
     * 예외를 숨기지 않고 명시적으로 처리할 수 있으며,
     *
     * 예외가 발생해도 스트림을 중단하지 않고 계속 처리할 수 있다.
     */

}
