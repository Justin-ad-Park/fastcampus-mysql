package com.example.javaLang.generic.streamtest;

import com.example.javaLang.generic.common.StreamTestGenerator;
import com.example.javaLang.generic.wildcards.Student;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    void 람다_메서드참조활용() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();

        System.out.println("==이름순 정렬==");
        students.sorted(Comparator.comparing(Student::getName)).forEach(System.out::println);

        System.out.println("==기본(성적순-나이-이름) 정렬==");
        students = StreamTestGenerator.getPersonStream();
        students.sorted(Comparator.comparing(Student::getRecord)
                        .thenComparing(Student::getAge)
                        .thenComparing(Student::getName)
                )
        .forEach(System.out::println);

        System.out.println("==기본(성적순-나이 어린순-이름) 정렬==");
        students = StreamTestGenerator.getPersonStream();
        students.sorted(Student::compareTo)
                .forEach(System.out::println);

    }

}
