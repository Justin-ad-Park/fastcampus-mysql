package com.example.javaLang.generic.wildcards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WildcardsTest {

    private Person youngestPerson = new Person("홍길동",17);
    private Person intermediatePerson = new Person("이몽룡",21);
    private Person ahphabetFirstPerson = new Person("김유신",28);

    private Supplier<Stream<Person>> peopleSupplier = () -> Stream.of(youngestPerson, intermediatePerson, ahphabetFirstPerson);

    private Stream<Person> getPersonStream() {
        return peopleSupplier.get();
    }

    /**
     * 전체 인원 중 가나다 순으로 이름이 가장 빠른 사람을 뽑는 방법
     * 최연소 인원을 뽑는 방법
     */
    @Test
    void windcardTest() {
        // 방법 1 (스트림 로직으로 풀어본다.)
        String alphaFirstName = getPersonStream()
                .map(Person::getName)
                .min(String::compareToIgnoreCase)
                .get();
        System.out.println(alphaFirstName);


        Assertions.assertEquals(ahphabetFirstPerson.getName(), alphaFirstName);

        int minAge = getPersonStream()
                .mapToInt(Person::getAge)
                .min()
                .getAsInt();
        System.out.println(minAge);
        Assertions.assertEquals(youngestPerson.getAge(), minAge);


        // 방법 2 : min(비교 로직)
        Person alphaFirst = getPersonStream()
                .min((t1, t2) ->  t1.getName().compareToIgnoreCase(t2.getName()))
                .get();
        System.out.println("가나다순 : " + alphaFirst);
        Assertions.assertEquals(ahphabetFirstPerson, alphaFirst);

        Person youngest = getPersonStream()
                .min((t1, t2) ->  Integer.compare(t1.getAge(), t2.getAge()))
                .get();
        System.out.println("최연소 : " + youngest);
        Assertions.assertEquals(youngestPerson.getAge(), minAge);


        // 방법 3 : Comparator<T> interface를 이용
        alphaFirst = getPersonStream().min(nameComparator).get();
        System.out.println("가나다 : " + alphaFirst);
        Assertions.assertEquals(ahphabetFirstPerson, alphaFirst);

        youngest = getPersonStream().min(ageComparator).get();
        System.out.println("최연소 : " + youngest);
        Assertions.assertEquals(youngestPerson.getAge(), minAge);


        // 방법 4 : Comparator<T>로 반복 메소드 분리
        alphaFirst = getMinPerson(getPersonStream(), nameComparator);
        System.out.println("가나다 : " + alphaFirst);

        youngest = getMinPerson(getPersonStream(), ageComparator);
        System.out.println("최연소 : " + youngest);


    }

    private static final <T extends Person> T getMinPerson(Stream<T> p, Comparator<T> comparator) {
        return p.min(comparator).get();
    }

    private static final Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private static final Comparator<Person> ageComparator = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());


}
