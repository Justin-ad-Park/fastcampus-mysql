package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda.ClassRoom;
import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda.StudentRegister;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.ConsumerLv7MethodChain.ClassRoomMaker.register;

public class ConsumerLv7MethodChain {

    public class ClassRoomMaker {
        public static ClassRoom makeClass(String className, StudentRegister... studentRegisters) {
            ClassRoom classRoom = new ClassRoom(className);

            // 스트림의 forEach는 스트림을 소비하는 Consumer 함수형 인터페이스를
            // 구현한 메서드 참조에 스트림을 소비시킨다.
            Stream.of(studentRegisters).forEach(
                s -> classRoom.addStudents(s.getStudents())
            );

            return classRoom;
        }

        public static StudentRegister register(String name) {
            StudentRegister builder = new StudentRegister();
            builder.register(name);

            return builder;
        }
    }

    @Test
    void Test() {
        ClassRoom cr = ClassRoomMaker.makeClass("수학",
                register("저스틴").register("조던"),
                register("리키").register("소냐")
        );

        System.out.println(cr);
    }
}
