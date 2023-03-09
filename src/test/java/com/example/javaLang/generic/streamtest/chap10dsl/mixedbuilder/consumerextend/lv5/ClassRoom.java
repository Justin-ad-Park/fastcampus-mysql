package com.example.javaLang.generic.streamtest.chap10dsl.mixedbuilder.consumerextend.lv5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassRoom {
        private String name;
        private List<Student> students = new ArrayList<>();

        public ClassRoom(final String name){
            this.name = name;
        }

        public void addStudent(Student student){
            students.add(student);
        }

        @Override
        public String toString() {
            String result = "className : "  +this.name + "\n";
            result += "Students: ";
            result += students.stream().map(Student::toString).collect(Collectors.joining(","));

            return result;
        }

    }