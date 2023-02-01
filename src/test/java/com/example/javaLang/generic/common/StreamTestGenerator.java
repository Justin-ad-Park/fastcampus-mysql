package com.example.javaLang.generic.common;

import com.example.javaLang.generic.wildcards.Student;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamTestGenerator {
    public static Student youngestPerson = new Student("홍길동",17, 80);
    public static Student intermediatePerson = new Student("이몽룡",21, 99);
    public static Student lowestScorePerson = new Student("온달",23, 45);
    public static Student ahphabetFirstPerson = new Student("김유신",27, 89);
    public static Student person5 = new Student("이순신",28, 89);
    public static Student person6 = new Student("장보고",28, 89);

    public static final Supplier<Stream<Student>> testPeopleSupplier = () -> Stream.of(youngestPerson, intermediatePerson, ahphabetFirstPerson, person5, person6, lowestScorePerson);
    public static final Stream<Student> getPersonStream() {
        return testPeopleSupplier.get();
    }

}
