package com.example.javaLang.generic.functional;

import com.example.javaLang.entity.Apple;
import com.example.javaLang.entity.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FunctionalProgramTest {
    private Apple greenApple = new Apple(Color.Green, 150);
    private Apple redApple = new Apple(Color.Red, 120);
    private Apple redBigApple = new Apple(Color.Red, 170);

    private List<Apple> apples = Arrays.asList(greenApple, redApple, redBigApple);

    @Test
    void formatterTest() {
        AppleFancyFormatter<Apple> appleFancyFormatter = new AppleFancyFormatter<>();

        final String output = appleFancyFormatter.accept(greenApple);
        System.out.println(output);

        Assertions.assertFalse(output.isEmpty(), "output 메시지가 존재하지 않습니다.");
    }

    @Test
    void printAppleByAnonymousClassTest() {
        Apple.PrintApple(apples, new AppleFancyFormatter() {
            @Override
            public String accept(Apple t) {
                return String.format("Color : %1$s, Weight : %2$dg", t.color().toString(), t.weight());
            }
        });
    }


    @Test
    void printApplesByInstanceOfClassTest() {
        Apple.PrintApple(apples, new AppleFancyFormatter<>());
    }

    @Test
    void printApplesByLambdaTest() {
        Apple.PrintApple(apples,
                (Apple apple) -> String.format(
                        "Color : %1$s, Weight : %2$dg"
                        , apple.color().toString()
                        , apple.weight()
                )
        );
    }

    /**
     * 제네릭으로 리스트 추상화
     */
    @Test
    void ListFilterTest() {
        List<Apple> redApples =
                ListFilter.filter(apples,
                        (Apple apple) -> Color.Red.equals(apple.color()));

        simplePrintApple(redApples);
    }

    private void simplePrintApple(List<Apple> apples) {
        Apple.PrintApple(apples, new AppleFancyFormatter<>());
    }

    @Test
    void ListSortTestByAnonymousClass() {
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.weight().compareTo(o2.weight());
            }
        });

        simplePrintApple(apples);
    }

    @Test
    void ListSortTestByLambda() {
        apples.sort(
                (o1, o2) -> o1.weight().compareTo(o2.weight())
        );

        simplePrintApple(apples);
    }

    @Test
    void ListSortTestByLambaReference() {
        apples.sort(weightComparator);

        simplePrintApple(apples);
    }

    private Comparator<Apple> weightComparator = (o1, o2) -> o1.weight().compareTo(o2.weight());

}
