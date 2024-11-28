package com.example.pmo.shippingpolicy;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClaimProductReqDTO {
    @Getter
    private long odOrderDetlId; // 주문 상세 번호

    @Getter
    private int claimCnt;   // 클레임 수량

    private ClaimProductReqDTO() {}

    public ClaimProductReqDTO(@NotNull final long odOrderDetlId, @NotNull final int claimCnt) {
        this.odOrderDetlId = Objects.requireNonNull(odOrderDetlId);
        this.claimCnt = Objects.requireNonNull(claimCnt);
    }
}
