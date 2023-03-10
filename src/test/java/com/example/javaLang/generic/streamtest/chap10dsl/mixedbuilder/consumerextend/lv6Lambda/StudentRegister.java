package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRegister {
    private List<Student> students = new ArrayList<>();

    public StudentRegister register(String name) {
        Student student = new Student();
        student.setName(name);
        students.add(student);

        return this;
    }

    public List<Student> getStudents() {
        return students;
    }
}
