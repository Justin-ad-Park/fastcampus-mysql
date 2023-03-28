package com.example.javaLang.generic.streamtest.chap16reactive;

import com.example.javaLang.generic.streamtest.chap16reactive.asynchronouspipeline.Discount;
import lombok.Getter;

import java.util.Random;

public record Shop(@Getter String shopName) {

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public String getDiscountPrice(String product) {
        Random random = new Random();

        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];

        return String.format("%s:%.2f:%s", shopName, price, code);
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
