package com.example.javaLang.generic.streamtest.chap10dsl.tradestock.nestedfunctionwithlambda;

import java.util.function.Supplier;

public class SimpleSupplier<T> implements Supplier<T> {
    final T value;

    protected SimpleSupplier(final T value) {
        this.value = value;
    }

    private SimpleSupplier() {
        value = null;
    }

    @Override
    public T get() {
        return value;
    }
}
