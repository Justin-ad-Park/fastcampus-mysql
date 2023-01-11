package com.example.javaLang.generic.wildcards;

import org.springframework.util.Assert;

public class Course<T> {
    private String name;
    private T[] students;
    private int index;
    private final int capacity;

    public Course(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.students = (T[])(new Object[capacity]);
        this.index = 0;
    }

    public String getName() { return name;}
    public T[] getStudents() {return students;}

    public void add(T t) {
        Assert.isTrue(index < capacity, "Capacity를 초과했습니다.");

        students[index] = t;
        index++;
    }
}
