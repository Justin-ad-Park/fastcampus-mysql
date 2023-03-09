package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv6Lambda;

import com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassRoom {
    private String name;
    private List<Student> students = new ArrayList<>();

    public ClassRoom(final String name){
        this.name = name;
    }

    public void addStudents(List<Student> students){
        this.students.addAll(students);
    }

    @Override
    public String toString() {
        String result = "className : "  +this.name + "\n";
        result += "Students: ";
        result += students.stream().map(Student::toString).collect(Collectors.joining(","));

        return result;
    }

}