package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda.ClassRoom;
import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda.StudentBuilder;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.ConsumerLv6withLambda.ClassRoomMaker.registerStudent;

public class ConsumerLv6withLambda {

    public class ClassRoomMaker {
        public static ClassRoom makeClass(String className, StudentBuilder... studentBuilders) {
            ClassRoom classRoom = new ClassRoom(className);

            Stream.of(studentBuilders).forEach(
                    s -> classRoom.addStudents(s.getStudents())
            );

            return classRoom;
        }

        public static StudentBuilder registerStudent(Consumer<StudentBuilder> consumer) {
            StudentBuilder builder = new StudentBuilder();
            consumer.accept(builder);

            return builder;
        }
    }


    @Test
    void Test() {
        ClassRoom cr = ClassRoomMaker.makeClass("수학",
                registerStudent(s -> s.register("저스틴").register("안나"))
                        );

        System.out.println(cr);

    }


}
