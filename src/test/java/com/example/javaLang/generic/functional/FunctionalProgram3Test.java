package com.example.javaLang.generic.functional;

import com.example.javaLang.generic.common.StreamTestGenerator;
import com.example.javaLang.generic.wildcards.Student;
import org.junit.jupiter.api.Test;

import java.util.function.*;
import java.util.stream.Stream;

// 함수형 인터페이스 Predicate<? super T> 를 인자로 요구하는 스트림 filter 메서드 사용 예)
public class FunctionalProgram3Test {

    private <T> void printStream(Stream<T> t) {
        t.forEach(System.out::println);
    }


    // 1. 직접 익명 클래스를 만들어 인터페이스를 구현한 메서드를 제공한다.
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

    // 2. 익명 클래스를 생성하는 팩토리 메서드를 통해 제공한다. 1번의 확장 개념
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


    // 3. 함수형 인터페이스를 구현한 클래스를 만들어 사용한다.
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

    // 4. Lambda 표현식으로 제공하거나, Lambda 표현식을 변수에 할당해서 제공한다.
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

    // 4-1. print도 Lambda 표현식을 변수에 할당하면 아래와 같이 간소화
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
