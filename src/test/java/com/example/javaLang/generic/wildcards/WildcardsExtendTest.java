package com.example.javaLang.generic.wildcards;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WildcardsExtendTest {

    private Student youngestPerson = new Student("홍길동",17, 80);
    private Student intermediatePerson = new Student("이몽룡",21, 99);
    private Student lowestScorePerson = new Student("온달",23, 45);
    private Student ahphabetFirstPerson = new Student("김유신",28, 89);


    /**
     * 전체 인원 중 가나다 순으로 이름이 가장 빠른 사람을 뽑는 방법
     * 최연소 인원을 뽑는 방법
     */
    @Test
    void windcardTest() {

        // 방법 5 : 다양한 타입을 비교할 수 있도록 재활용성을 극대화 할 수 없을까?
        // Comparator<T>에 getter와 비교에 사용할 comparator
        //
        Student alphaFirst = getMinPerson(getPersonStream(), multitypeComparator(Person::getName, stringComparator));
        System.out.println("가나다 : " + alphaFirst);

        Student lowestRecord = getMinPerson(getPersonStream(), multitypeComparator(Student::getAge, intComparator));
        System.out.println("최연소 : " + lowestRecord);

        Student youngestRecord = getMinPerson(getPersonStream(), multitypeComparator(Student::getRecord, intComparator));
        System.out.println("최하점 : " + youngestRecord);

        Student highestRecord = getMinPerson(getPersonStream(), multitypeComparator(Student::getRecord, intComparator.reversed()));
        System.out.println("최고점 : " + highestRecord);

    }

    private final Supplier<Stream<Student>> peopleSupplier = () -> Stream.of(youngestPerson, intermediatePerson, lowestScorePerson, ahphabetFirstPerson);
    private final Stream<Student> getPersonStream() {
        return peopleSupplier.get();
    }

    private  final <T> T getMinPerson(Stream<T> p, Comparator<T> comparator) {
        return p.min(comparator).get();
    }


    /**
     *
     * @param f : 타입(T)의 비교할 값을 리턴하는 함수(getter)
     * @param comparator : f가 리턴한 값을 비교하는 Comparator
     * @param <T> : 타입
     * @param <R> : 비교할 값의 형식
     * @return : Comparator<T>로 동일한 타입 두 개를 받아서 비교하는 람다 함수를 리턴
     */
    private static final <T, R> Comparator<T> multitypeComparator(Function<T, R> f, Comparator<R> comparator) {
        return (T o1, T o2) -> comparator.compare(f.apply(o1), f.apply(o2));
    }

    private static final Comparator<String> stringComparator = (String r1, String r2) -> {
        return r1.compareToIgnoreCase(r2);
    };

    private static final Comparator<Integer> intComparator = (Integer r1, Integer r2) -> {
        return Integer.compare(r1, r2);
    };

}
