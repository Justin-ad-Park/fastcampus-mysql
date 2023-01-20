package com.example.javaLang.generic.functional;

import com.example.javaLang.generic.common.StreamTestGenerator;
import com.example.javaLang.generic.wildcards.Student;
import org.junit.jupiter.api.Test;

import java.util.function.*;
import java.util.stream.Stream;

public class FunctionalProgram3Test {

    private <T> void printStream(Stream<T> t) {
        t.forEach(System.out::println);
    }

    @Test
    void inheritFilterTest() {

        Stream<Student> students = StreamTestGenerator.getPersonStream();

        Stream<Student> youngStudents = students.filter(new Predicate<Student>() {
            @Override
            public boolean test(Student student) {
                return student.getAge() < 20;
            }
        });

        printStream(youngStudents);
    }

    private static Predicate<Student> YoungFilter() {
        return new Predicate<Student>() {
            @Override
            public boolean test(Student t) {
                return t.getAge() < 20;
            }
        };
    }

    @Test
    void StaticMethodOverrideTest() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();
        Stream<Student> youngStudents = students.filter(YoungFilter());

        printStream(youngStudents);
    }


    class YoungFilterClass<T extends Student> implements Predicate<T> {

        @Override
        public boolean test(T t) {
            return t.getAge() < 20;
        }
    }

    @Test
    void implementsClassTest() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();
        Stream<Student> youngStudents = students.filter(new YoungFilterClass<>());
        printStream(youngStudents);

        students = StreamTestGenerator.getPersonStream();
        YoungFilterClass<Student> yf = new YoungFilterClass<>();
        youngStudents = students.filter(yf);
        printStream(youngStudents);

    }

    @Test
    void lambdaTest() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();
        Stream<Student> youngStudents = students.filter((t)-> t.getAge() < 20 );
        printStream(youngStudents);

        Predicate<Student> youngFilter = (t) -> t.getAge() < 20;
        students = StreamTestGenerator.getPersonStream();
        youngStudents = students.filter(youngFilter);
        printStream(youngStudents);

    }


    @Test
    void allLambdaTest() {
        Stream<Student> students = StreamTestGenerator.getPersonStream();
        students.filter((t)-> t.getAge() < 20 )
                .forEach(System.out::println);

        Consumer printT = (t) -> System.out.println(t);
        students = StreamTestGenerator.getPersonStream();
        students.filter((t)-> t.getAge() < 20 )
                .forEach(printT);

    }

}
