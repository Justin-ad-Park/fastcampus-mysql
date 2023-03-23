package com.example.javaLang.generic.streamtest.chap16reactive;

import lombok.Getter;

import java.util.Random;

public record Shop(@Getter String shopName) {

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay(1);

        Random random = new Random();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
