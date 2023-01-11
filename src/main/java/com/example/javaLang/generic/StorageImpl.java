package com.example.javaLang.generic;

public class StorageImpl<T> implements Storage<T> {
    private T[] array;

    public StorageImpl(int capacity)
    {
        this.array = (T[])new Object[capacity];
        //this.array = (T[])new T[capacity];
        /**
         제네릭 타입은 불공변하며 타입 안정성을 보장해 줄 수 없으며,
         이로 인해 제너릭 배열을 직접 생성할 수 없다.
         */
    }

    @Override
    public void add(T item, int index) {
        array[index] = item;
    }

    @Override
    public T get(int index) {
        return array[index];
    }
}
