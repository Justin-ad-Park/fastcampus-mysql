package com.example.pmo.shippingpolicy;

import com.example.pmo.shippingpolicy.processor.ClaimShippingProcessor;
import com.example.pmo.shippingpolicy.processor.implement.*;

import java.util.Arrays;
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
        return Arrays.stream(values())
                .filter(e -> e.claimType.equals(claimType) && e.claimResponsibility.equals(claimResponsibility))
                .findFirst().get().getInstance();

    }

}

