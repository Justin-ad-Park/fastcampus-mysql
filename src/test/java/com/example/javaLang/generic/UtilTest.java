package com.example.javaLang.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UtilTest {

    @Test
    public void testBoxing() {
        final int value = 100;
        Box<Integer> box1 = Util.boxing(value);

        Assertions.assertEquals(value, box1.get());
    }


    @Test
    void testCompare() {
        Pair<Integer, String> p1 = new Pair<>(1, "Apple");
        Pair<Integer, String> p2 = new Pair<>(1, "Apple");
        Pair<Integer, String> p3 = new Pair<>(1, "Orange");
        Pair<Integer, String> p4 = new Pair<>(2, "Apple");

        Assertions.assertTrue(Util.compare(p1, p2));
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
        int result1 = Util.compare(10, 10);
        int result2 = Util.compare(10, 11);
        int result3 = Util.compare(10, 9);

        boolean result4 = Util.compare("10", "20");
        boolean result5 = Util.compare("10", 10);
        boolean result6 = Util.compare("10", 20);

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
}
