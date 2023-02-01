package com.example.javaLang.generic.streamtest;

import com.example.javaLang.generic.common.StreamTestGenerator;
import com.example.javaLang.generic.wildcards.Student;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamGroupBy {

    @Test
    void 스트림그룹핑() {
        Stream<Student> people = StreamTestGenerator.getPersonStream();

//        Map<Integer, List<Student>> studentByRecord =
//                people.
    }

    @Test
    void Math_FloorDiv() {
        int result = 12345 / 10 * 10;

        System.out.println(result);

    }
}
