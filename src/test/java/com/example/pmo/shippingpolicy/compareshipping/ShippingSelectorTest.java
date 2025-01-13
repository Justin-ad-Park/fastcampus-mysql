package com.example.pmo.shippingpolicy.compareshipping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShippingSelectorTest {

    @Test
    void 복합비교() {
        // given
        List<Shipping> shippingList = new ArrayList<>();

        // 다양한 인스턴스 추가
        shippingList.add(new Shipping(1,5, 2, ShippingCondition.CONDITIONAL_FREE_SHIPPING));
        shippingList.add(new Shipping(2, 0, 1, ShippingCondition.FREE_SHIPPING));
        shippingList.add(new Shipping(3, 10, 5, ShippingCondition.CONDITIONAL_FREE_SHIPPING));
        shippingList.add(new Shipping(4, 0, 3, ShippingCondition.FREE_SHIPPING));
        shippingList.add(new Shipping(5, 7, 4, ShippingCondition.CONDITIONAL_FREE_SHIPPING));


        // when
        Shipping shipping = ShippingSelector.getBestShipping(shippingList);

        System.out.println(shipping.toString());

        // then
        Assertions.assertEquals(2, shipping.getShippingId());

    }

    @Test
    void 조건부배송비끼리비교() {
        // given
        List<Shipping> shippingList = new ArrayList<>();

        // 다양한 인스턴스 추가
        shippingList.add(new Shipping(3, 5, 5, ShippingCondition.CONDITIONAL_FREE_SHIPPING));
        shippingList.add(new Shipping(5, 7, 4, ShippingCondition.CONDITIONAL_FREE_SHIPPING));
        shippingList.add(new Shipping(1,5, 2, ShippingCondition.CONDITIONAL_FREE_SHIPPING));


        // when
        Shipping shipping = ShippingSelector.getBestShipping(shippingList);

        System.out.println(shipping.toString());

        // then
        Assertions.assertEquals(1, shipping.getShippingId());

    }
}