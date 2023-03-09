package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBuilder {
    private List<Student> students = new ArrayList<>();

    public StudentBuilder register(String name) {
        Student student = new Student();
        student.setName(name);
        students.add(student);

        return this;
    }

    public List<Student> getStudents() {
        return students;
    }
}
