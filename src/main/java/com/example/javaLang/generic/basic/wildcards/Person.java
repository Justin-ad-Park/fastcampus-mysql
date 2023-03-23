package com.example.javaLang.generic.basic.wildcards;

public class Person {
    private String name;
    private int age;

    public Person(String name) {
        this.name = name;
    }
    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return name + " : " + age;
    }
}
