package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.ClaimResponsibility;
import com.example.pmo.shippingpolicy.ClaimType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public enum ClaimProcessorSelector {
    PARTIAL_CANCELLATION_BY_BUYER(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY, PartialCancelationByBuyer::new),
    PARTIAL_CANCELLATION_BY_SELLER(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.SELLER_RESPONSIBILITY, PartialClaimBySeller::new),
    FULL_CANCELLATION_BY_BUYER(ClaimType.FULL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY, ClaimFullRefund::new),
    FULL_CANCELLATION_BY_SELLER(ClaimType.FULL_CANCELLATION, ClaimResponsibility.SELLER_RESPONSIBILITY, ClaimFullRefund::new),
    PARTIAL_RETURN_BY_BUYER(ClaimType.PARTIAL_RETURN, ClaimResponsibility.BUYER_RESPONSIBILITY, PartialReturnByBuyer::new),
    PARTIAL_RETURN_BY_SELLER(ClaimType.PARTIAL_RETURN, ClaimResponsibility.SELLER_RESPONSIBILITY, PartialClaimBySeller::new),
    FULL_RETURN_BY_BUYER(ClaimType.FULL_RETURN, ClaimResponsibility.BUYER_RESPONSIBILITY, FullReturnByBuyer::new),
    FULL_RETURN_BY_SELLER(ClaimType.FULL_RETURN, ClaimResponsibility.SELLER_RESPONSIBILITY, ClaimFullRefund::new)
    ;


    private ClaimType claimType;
    private ClaimResponsibility claimResponsibility;
    private Supplier<ClaimShippingProcessor> constructor;

    ClaimProcessorSelector(ClaimType claimType, ClaimResponsibility claimResponsibility, Supplier<ClaimShippingProcessor> constructor) {
        this.claimType = claimType;
        this.claimResponsibility = claimResponsibility;
        this.constructor = constructor;
    }

    public ClaimShippingProcessor getInstance() {
        return constructor.get();
    }

    public static ClaimShippingProcessor getInstance(ClaimType claimType, ClaimResponsibility claimResponsibility) {
        ClaimProcessorSelector selector = CACHE.get(new Key(claimType, claimResponsibility));

        if (selector == null) {
            throw new IllegalArgumentException("No matching processor found for ClaimType: "
                    + claimType + ", Responsibility: " + claimResponsibility);
        }
        return selector.getInstance();
    }

    /**
     * @deprecated
     * Enum을 순환해서 객체를 찾는 방식이 성능이 좋지 않아 static map을 사용하는 방식으로 변경
     *
     * @param claimType
     * @param claimResponsibility
     * @return
     */
    @Deprecated
    protected static ClaimShippingProcessor getInstanceOldVersion(ClaimType claimType, ClaimResponsibility claimResponsibility) {
        return Arrays.stream(values())
                .filter(e -> e.claimType.equals(claimType) && e.claimResponsibility.equals(claimResponsibility))
                .findFirst().get().getInstance();

    }

    private static final Map<Key, ClaimProcessorSelector> CACHE = new HashMap<>();

    static {
        for (ClaimProcessorSelector selector : values()) {
            CACHE.put(new Key(selector.claimType, selector.claimResponsibility), selector);
        }
    }

    private static class Key {
        private final ClaimType claimType;
        private final ClaimResponsibility claimResponsibility;

        public Key(ClaimType claimType, ClaimResponsibility claimResponsibility) {
            this.claimType = claimType;
            this.claimResponsibility = claimResponsibility;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Key key = (Key) o;
//            return claimType == key.claimType && claimResponsibility == key.claimResponsibility;
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(claimType, claimResponsibility);
//        }
    }

}

