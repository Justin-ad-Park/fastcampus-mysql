package com.example.pmo.shippingpolicy;

public enum ClaimResponsibility {
    BUYER_RESPONSIBILITY("구매자 귀책"),
    SELLER_RESPONSIBILITY("판매자 귀책");

    private String name;

    ClaimResponsibility(String name) {
        this.name = name;
    }
}

