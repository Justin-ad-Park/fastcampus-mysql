package com.example.javaLang.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtilTest {

    @Test
    public void testBoxing() {
        final int value = 100;
        Box<Integer> box1 = Util.boxing(value);

        Assertions.assertEquals(value, box1.get());

        Box<String> box2 = Util.<String>boxing("Test");


        var box4 = Util.<Long>boxing(100L);
        box4 = Util.boxing(200L);


        var box3 = Util.boxing(100L);
        // box3 = Util.boxing(100);

    }


    @Test
    void testCompare() {
        Pair<Integer, String> p1 = new Pair<>(1, "Apple");
        Pair<Integer, String> p2 = new Pair<>(1, "Apple");
        Pair<Integer, String> p3 = new Pair<>(1, "Orange");
        Pair<Integer, String> p4 = new Pair<>(2, "Apple");

        Assertions.assertTrue(Util.<Integer, String>compare(p1, p2));   //제네릭 타입 명시적 지정
        Assertions.assertFalse(Util.compare(p1, p3), () -> "p1 != p3 에러 발생");
        Assertions.assertFalse(Util.compare(p1, p4));
        Assertions.assertFalse(Util.compare(p3, p4));

        Assertions.assertAll(
                () -> Assertions.assertTrue(Util.compare(p1, p2)),
                () -> Assertions.assertFalse(Util.compare(p1, p3), () -> "p1 != p3 Error at assertAll"),
                () -> Assertions.assertFalse(Util.compare(p1, p4)),
                () -> Assertions.assertFalse(Util.compare(p3, p4))
        );
    }

    @Test
    void compareNumber() {
        var result1 = Util.compare(10, 10);
        int result2 = Util.<Number>compare(10, 11);
        int result3 = Util.compare(10, 9);

        var result4 = Util.compare("10", "20");
        var result5 = Util.compare("10", 10);
        boolean result6 = Util.compare("10", 20);
        var result7 = Util.<String, Number>compare("10", 10);

        Assertions.assertEquals(0, result1);
        Assertions.assertEquals(-1, result2);
        Assertions.assertEquals(1, result3);

        Assertions.assertFalse(result4);
        Assertions.assertTrue(result5, () -> "`10`, 10 compare");
        Assertions.assertFalse(result6, () -> "`10`, 20 compare");
    }

    @Test
    void getBox() {
        String model = "MD001";
        String name = "최신모델";

        Box<String> box = Util.getBox(model, name);

        Assertions.assertEquals(model, box.get());
    }

    @Test
    void StringFormatter() {
        System.out.println(
                String.format("%1$10d\t %2$9d\t %3$20s\t %4$s", 8888812, 12, LocalDateTime.now(), "Test Abc Def")
        ); ;
    }

    @Test
    void 제네릭_타입체크() {
        List<String> list = new ArrayList<>();
        list.add("Hello");

        String str = list.get(0);

        // 제네릭 타입에서 String 으로 선언되어 컴파일 에러가 발생함
        //list.add(3L);
        //Long value = list.get(1);
    }
}
