package com.example.pmo.shippingpolicy.compareshipping;

import java.util.List;
import java.util.Optional;

public class ShippingSelector {
    public static Shipping getBestShipping(List<Shipping> shippingList) {

        // 가장 좋은 배송 조건 찾기
        Optional<Shipping> bestShipping = shippingList.stream()
                .sorted(Shipping.SHIPPING_COMPARATOR)
                .findFirst();

        // 결과 출력
        return bestShipping.get();
    }

}
