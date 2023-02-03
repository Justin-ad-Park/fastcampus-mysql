package com.example.javaLang.generic.streamtest;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream186Pythagorean {

    private Consumer<int[]> printPythagorianTriples = t -> System.out.println(String.format("%d, %d, %d", t[0], t[1], t[2]));
    private Consumer<double[]> printPythagorianTriplesToDouble = t -> System.out.println(String.format("%s, %s, %s", t[0], t[1], t[2]));

    @Test
    void 피타고라스수() {
        StopWatch sw = new StopWatch();
        sw.start();

       Stream<int[]> pythagoreanTriples =
            IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                    IntStream.rangeClosed(a, 100)
                        .filter(isPythagorian(a))
                        .mapToObj(setPythagoreanTriples(a))
                        );

       pythagoreanTriples.limit(5)
           .forEach(printPythagorianTriples);

        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    @NotNull
    private IntFunction<int[]> setPythagoreanTriples(Integer a) {
        return b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)};
    }

    @NotNull
    private IntFunction<double[]> setPythagoreanTriplesToDouble(Integer a) {
        return b -> new double[]{a, b, Math.sqrt(a * a + b * b)};
    }

    @NotNull
    private IntPredicate isPythagorian(Integer a) {
        return b -> Math.sqrt(a * a + b * b) % 1 == 0;
    }

    /*
    sqrt을 한번만 사용하는 아래 로직이 두 번 사용하는 위의 로직과 비교 87%로 효율적
     */
    @Test
    void 피타고라스수2() {
        StopWatch sw = new StopWatch();
        sw.start();

        Stream<double[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100)
                        .boxed()
                        .flatMap(a ->
                            IntStream.rangeClosed(a, 100)
                                .mapToObj(
                                    b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                                .filter(t -> t[2] % 1 == 0)
                        );

        pythagoreanTriples.limit(5)
                .forEach(printPythagorianTriplesToDouble);

        sw.stop();
        System.out.println(sw.prettyPrint());

    }
}
