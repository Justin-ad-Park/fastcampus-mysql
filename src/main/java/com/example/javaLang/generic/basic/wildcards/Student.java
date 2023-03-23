package com.example.javaLang.generic.basic.wildcards;

import org.jetbrains.annotations.NotNull;

public class Student extends Person implements Comparable<Student> {
    private int record;

    public Student(String name) {
        super(name);
    }

    public Student(String name, int age) {
        super(name, age);
    }

    public Student(String name, int age, int record) {
        super(name, age);

        this.record = record;
    }

    public int getRecord() {
        return this.record;
    }

    public Student getStudent() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("name : %1$s, age : %2$d, record : %3$d", this.getName(), this.getAge(), this.getRecord());
    }


    @Override
    public int compareTo(@NotNull Student other) {
        int result = Integer.compare(this.getRecord(), other.getRecord());
        if(result != 0) return result;

        //나이 어린 경우 고득점
        result = Integer.compare(other.getAge(), this.getAge());
        if(result != 0) return result;

        // 마지막 가나다순
        result = this.getName().compareTo(other.getName());

        return result;
    }
}
