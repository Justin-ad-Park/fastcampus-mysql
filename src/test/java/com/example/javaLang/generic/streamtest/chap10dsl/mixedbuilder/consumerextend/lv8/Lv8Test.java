package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv8;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.ClassRoom;
import org.junit.jupiter.api.Test;

public class Lv8Test {
    @Test
    void Test() {
        ClassRoom cr = ClassRoomMaker.makeClass("수학")
                .register("저스틴")
                .register("안니")
                .register("마이크")
                .finish();

        System.out.println(cr);
    }
}
