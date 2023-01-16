package com.example.javaLang.generic.wildcards;

public class Student extends Person {
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

    @Override
    public String toString() {
        return String.format("name : %1$s, age : %2$d, record : %3$d", this.getName(), this.getAge(), this.getRecord());
    }

}
