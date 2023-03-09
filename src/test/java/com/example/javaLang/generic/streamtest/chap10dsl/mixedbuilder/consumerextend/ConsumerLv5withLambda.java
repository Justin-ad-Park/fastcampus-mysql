package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.ClassRoom;
import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.StudentBuilder;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.ConsumerLv5withLambda.ClassRoomMaker.registerStudent;

public class ConsumerLv5withLambda {

    public class ClassRoomMaker {
        public static ClassRoom makeClass(String className, StudentBuilder... students) {
            ClassRoom classRoom = new ClassRoom(className);

            Stream.of(students).forEach(
                    s -> classRoom.addStudent(s.getStudent())
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
                registerStudent(s -> s.register("저스틴")),
                registerStudent(s -> s.register("안나")
                        ));

        System.out.println(cr);

    }


}
