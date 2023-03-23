package com.example.javaLang.generic.streamtest.chap16reactive.asynchronouspipeline;

import com.example.javaLang.generic.streamtest.chap16reactive.Shop;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int discountRate;


        Code(final int discountRate) {
            this.discountRate = discountRate;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    public static double apply(double price, Code code) {
        Shop.delay(1);
        return price * (100 - code.discountRate) / 100;
    }
}
