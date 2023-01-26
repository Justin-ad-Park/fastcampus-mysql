package com.example.javaLang.generic.functional;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 람다 형식추론의 이해
 */
public class FunctionalProgram4Test {

    private static <T> Consumer<T> printAny() {
        return t -> System.out.println(t.toString());
    }

    private static Consumer<? super Number> printNumber() {
        return t -> System.out.println(t.toString());
    }
    // 함수형 인자로 Comsumer<T> 가능
    private static <T> void printAnyByFunction(Consumer<T> prt, T t) {
        prt.accept(t);
    }

    //제너릭 <T extends String>으로 선언 후 함수형 인자로 <? super T> 가능
    //왜냐? T exteds String 조건에 만족하는 인자는 모두 <? Super T> 조건에 부합하니까 가능함
    private static <T extends String> void printStringByFunction(Consumer<? super T> prt, T t) {
        prt.accept(t);
    }

    // <T extends String>를 <? extends T>로 변환은 불가능
    // String에게 3세대의 자식 클래스 계층이 있다고 할 때,
    // 3세대를 2세대로 변환은 가능하지만,
    // 2세대를 3세대로 변환은 불가능
    // (부모는 자식을 품을 수 있지만, 자식은 부모를 품지 못한다는 인생의 진리가 개발 언어에도 녹가 있는...)
    /*
    private static <T extends String> void printStringByFunction(Consumer<? extends T> prt, T t) {
        prt.accept(t);
    }
    */


    @Test
    void test() {
        Consumer<String> prtAny = printAny();
        prtAny.accept("Hello world!!");

        var prtNumber = printNumber();
        prtNumber.accept(1);

        Stream<String> s = Arrays.asList("Test", "Abc", "Hello List").stream();
        s.forEach(prtAny);

        Stream<Long> sl = Arrays.asList(1L, 2L, 100L).stream();
        sl.forEach(prtNumber);

        printStringByFunction(printAny(), "이건 된다.");
        //printStringByFunction(printNumber(), "이건 안된다.");
        printAnyByFunction(printAny(), 1L);
        printAnyByFunction(printNumber(), 1L);

    }

    @Test
    void test2() {
        // 하나 더 해보자.
        Consumer<? super String> prtLambda = (t) -> System.out.println(t);
        prtLambda.accept( "Test" );
        printAnyByFunction(prtLambda, "Test");
    }

    Runnable r = () -> System.out.println("Running");

    @Test
    void run() {
        r.run();
    }

    @FunctionalInterface
    interface Action<T> {
        void act(T t);
    }

    @FunctionalInterface
    interface Doit {
        void run();
    }

    public void execute(Runnable runnable) {
        runnable.run();
    }

    public void execute(Doit doit) {
        doit.run();
    }
    public <T extends String> void execute(Action<? super String> action, T t) {
        action.act(t);
    }

    @Test
    void act() {
        var t = "Actor";

        execute((Runnable)() -> System.out.println("Runnable"));
        execute((Doit)() -> System.out.println("Runnable2"));

        // 형식 추론 예제
        execute((u) -> System.out.println(u + "'s Action"), t);
        execute((Action)(u) -> System.out.println(u + "'s Action2"), t);
        execute((Action<String>)(u) -> System.out.println(u + "'s Action3"), t);
        execute((Action<String>)(String u) -> System.out.println(u + "'s Action3"), t);
    }


}
