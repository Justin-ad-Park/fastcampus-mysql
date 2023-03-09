package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5;

public class StudentBuilder {
    private Student student;

    public StudentBuilder register(String name) {
        student = new Student();
        student.setName(name);

        return this;
    }

    public Student getStudent() {
        return student;
    }
}
