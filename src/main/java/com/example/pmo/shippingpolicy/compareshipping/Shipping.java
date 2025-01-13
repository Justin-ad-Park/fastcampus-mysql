package com.example.pmo.shippingpolicy.compareshipping;


import lombok.Getter;

import java.util.Comparator;
import java.util.Objects;

@Getter
public class Shipping {
    private long shippingId;
    private int shippingPrice;
    private int claimShippingPrice;
    private ShippingCondition shippingCondition;

    public Shipping(long shippingId, int shippingPrice, int claimShippingPrice, ShippingCondition shippingCondition) {
        this.shippingId = shippingId;
        this.shippingPrice = shippingPrice;
        this.claimShippingPrice = claimShippingPrice;
        this.shippingCondition = shippingCondition;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "shippingId=" + shippingId +
                "shippingPrice=" + shippingPrice +
                ", claimShippingPrice=" + claimShippingPrice +
                ", shippingCondition=" + shippingCondition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipping shipping = (Shipping) o;
        return Long.compare(shipping.shippingPrice, shippingPrice) == 0 &&
                Long.compare(shipping.claimShippingPrice, claimShippingPrice) == 0 &&
                shippingCondition == shipping.shippingCondition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shippingPrice, claimShippingPrice, shippingCondition);
    }

    // Static Comparator for Shipping
    public static final Comparator<Shipping> SHIPPING_COMPARATOR = (s1, s2) -> {
        // 1. Compare by shipping price
        int result = Double.compare(s1.getShippingPrice(), s2.getShippingPrice());
        if (result != 0) return result;

        // 2. Compare by claim shipping price
        result = Double.compare(s1.getClaimShippingPrice(), s2.getClaimShippingPrice());
        if (result != 0) return result;

        // 3. Compare by shipping condition
        return Objects.compare(
                s1.getShippingCondition(),
                s2.getShippingCondition(),
                Comparator.comparingInt(condition -> condition == ShippingCondition.FREE_SHIPPING ? 0 : 1)
        );
    };
}