package com.example.javaLang.generic.streamtest.chap15.reactiveprogramming;

import java.util.ArrayList;
import java.util.List;

public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {
    private int value = 0;
    private String name;
    private List<Subscriber> subscribers = new ArrayList<>();

    public SimpleCell(String name) {
        this.name = name;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void onChange(Integer newValue) {
        this.value = newValue;

        System.out.println(this.name + ":" + this.value);
        notifyAllSubscribers();
    }

    private void notifyAllSubscribers() {
        subscribers.forEach(subscriber -> subscriber.onChange(this.value));
    }
}
