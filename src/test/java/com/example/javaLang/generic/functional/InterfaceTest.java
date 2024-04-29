package com.example.javaLang.generic.functional;

import java.util.List;


public class InterfaceTest {
    @FunctionalInterface
    public interface Save {
        void save();
    }

    public static class ClassA implements Save {
        @Override
        public void save() {
            System.out.println("Save A");
        }
    }

    public static class ClassB implements Save {
        @Override
        public void save() {
            System.out.println("Save B");
        }
    }

    public static class ClassC implements Save {
        @Override
        public void save() {
            System.out.println("Save C");
        }
    }


    public static void main(String[] args) {

        ClassA a = new ClassA();
        ClassB b = new ClassB();
        ClassC c = new ClassC();

        List<Save> saveItems = List.of(a,b,c);

        for (Save item : saveItems) {
            item.save();
        }
    }
}
