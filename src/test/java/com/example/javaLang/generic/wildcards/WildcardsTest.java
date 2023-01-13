package com.example.javaLang.generic.wildcards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
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



        // 방법 5 : 다양한 타입을 비교할 수 있도록 재활용성을 극대화 할 수 없을까?
        // Comparator<T>에 getter와 비교에 사용할 comparator
        //
        alphaFirst = getMinPerson(getPersonStream(), superComparator(Person::getName, stringComparator));
        System.out.println("가나다 : " + alphaFirst);

        youngest = getMinPerson(getPersonStream(), superComparator(Person::getAge, intComparator));
        System.out.println("최연소 : " + youngest);

    }

    private <T extends Person> T getMinPerson(Stream<T> p, Comparator<T> comparator) {
        return p.min(comparator).get();
    }

    private final Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private final Comparator<Person> ageComparator = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());


    /***
     *
     */
    public static <A, B> Comparator<A> superComparator(Function<? super A, ? extends B> f, Comparator<B> cb) {
        return (A a1, A a2) -> cb.compare(f.apply(a1), f.apply(a2));
    }

    Comparator<String> stringComparator = (String r1, String r2) -> {
        return r1.compareToIgnoreCase(r2);
    };

    Comparator<Integer> intComparator = (Integer r1, Integer r2) -> {
        return Integer.compare(r1, r2);
    };

}
