package com.example.javaLang.generic.streamtest.chap05;

import com.example.javaLang.generic.teststreamprovider.StreamTestGenerator;
import com.example.javaLang.generic.basic.wildcards.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamMethodCombinationTest {

    @Test
    void 람다_메서드참조활용() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();

        System.out.println("==이름순 정렬==");
        students.sorted(Comparator.comparing(Student::getName)).forEach(System.out::println);

        //Comperator 연결
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

    @Test
    void Predicate조합() {
        var students = StreamTestGenerator.getPersonStream();

        Predicate<Student> predicateYoungBoys = t->t.getAge() < 20;
        var predicateOldBoys = predicateYoungBoys.negate();

        var youngBoys = students.filter(predicateYoungBoys);

        System.out.println("\n===20세 미만===");
        youngBoys.forEach(System.out::println);


        students = StreamTestGenerator.getPersonStream();
        var oldBoys = students.filter(predicateOldBoys);

        System.out.println("\n===20세 이상===");
        oldBoys.forEach(System.out::println);

        students = StreamTestGenerator.getPersonStream();
        var oldSmartBoys = students
                .filter(predicateOldBoys.and(t -> t.getRecord() >= 80))
                .sorted(Comparator.comparing(Student::getRecord).reversed());

        System.out.println("\n===20세, 80점 이상===");
        oldSmartBoys.forEach(System.out::println);
    }

    @Test
    void Function조합() {
        Function<Integer, Integer> intAddOne = x -> x + 1;
        Function<Integer, Integer> int2times = x -> x * 2;
        Function<Integer, Integer> int3times = x -> x * 3;
        Function<Integer, Integer> combine = intAddOne.andThen(int2times).andThen(int3times);
        Function<Integer, Integer> compose = intAddOne.compose(int2times).compose(int3times);  //역순으로 처리됨

        var result1 = combine.apply(1);
        Assertions.assertEquals((1+1)*2*3, result1);

        var result2 = combine.apply(2);
        Assertions.assertEquals((2+1)*2*3, result2);

        var result3 = compose.apply(2);
        Assertions.assertEquals( ((2*3)*2)+1, result3);

        System.out.println(result1 + "," + result2 + "," + result3);


    }

}
