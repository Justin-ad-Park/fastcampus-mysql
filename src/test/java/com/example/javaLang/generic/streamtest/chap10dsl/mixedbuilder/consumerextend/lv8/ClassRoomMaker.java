package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv8;


import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.ClassRoom;
import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.Student;

public class ClassRoomMaker {
    private final ClassRoom classRoom;

    public ClassRoomMaker(String className) {
        classRoom = new ClassRoom(className);
    }

    public static ClassRoomMaker makeClass(String className) {
        return new ClassRoomMaker(className);
    }

    public ClassRoomMaker register(String name) {
        Student st = new Student();
        st.setName(name);
        classRoom.addStudent(st);

        return this;
    }

    public ClassRoom finish() {
        return classRoom;
    }

}
