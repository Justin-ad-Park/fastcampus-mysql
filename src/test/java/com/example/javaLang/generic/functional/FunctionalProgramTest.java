package com.example.javaLang.generic.functional;

import com.example.javaLang.entity.ColorApple;
import com.example.javaLang.entity.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FunctionalProgramTest {
    private ColorApple greenColorApple = new ColorApple(Color.Green, 150);
    private ColorApple redColorApple = new ColorApple(Color.Red, 120);
    private ColorApple redBigColorApple = new ColorApple(Color.Red, 170);

    private List<ColorApple> colorApples = Arrays.asList(greenColorApple, redColorApple, redBigColorApple);

    @Test
    void formatterTest() {
        AppleFancyFormatter<ColorApple> appleFancyFormatter = new AppleFancyFormatter<>();

        final String output = appleFancyFormatter.accept(greenColorApple);
        System.out.println(output);

        Assertions.assertFalse(output.isEmpty(), "output 메시지가 존재하지 않습니다.");
    }

    @Test
    void printAppleByAnonymousClassTest() {
        ColorApple.PrintApple(colorApples, new AppleFancyFormatter() {
            @Override
            public String accept(ColorApple t) {
                return String.format("Color : %1$s, Weight : %2$dg", t.color().toString(), t.weight());
            }
        });
    }


    @Test
    void printApplesByInstanceOfClassTest() {
        ColorApple.PrintApple(colorApples, new AppleFancyFormatter<>());
    }

    @Test
    void printApplesByLambdaTest() {
        ColorApple.PrintApple(colorApples,
                (ColorApple colorApple) -> String.format(
                        "Color : %1$s, Weight : %2$dg"
                        , colorApple.color().toString()
                        , colorApple.weight()
                )
        );
    }

    /**
     * 제네릭으로 리스트 추상화
     */
    @Test
    void ListFilterTest() {
        List<ColorApple> redColorApples =
                ListFilter.filter(colorApples,
                        (ColorApple colorApple) -> Color.Red.equals(colorApple.color()));

        simplePrintApple(redColorApples);
    }

    private void simplePrintApple(List<ColorApple> colorApples) {
        ColorApple.PrintApple(colorApples, new AppleFancyFormatter<>());
    }

    @Test
    void ListSortTestByAnonymousClass() {
        colorApples.sort(new Comparator<ColorApple>() {
            @Override
            public int compare(ColorApple o1, ColorApple o2) {
                return o1.weight().compareTo(o2.weight());
            }
        });

        simplePrintApple(colorApples);
    }

    @Test
    void ListSortTestByLambda() {
        colorApples.sort(
                (o1, o2) -> o1.weight().compareTo(o2.weight())
        );

        simplePrintApple(colorApples);
    }

    @Test
    void ListSortTestByLambaReference() {
        colorApples.sort(weightComparator);

        simplePrintApple(colorApples);
    }

    private Comparator<ColorApple> weightComparator = (o1, o2) -> o1.weight().compareTo(o2.weight());

}
