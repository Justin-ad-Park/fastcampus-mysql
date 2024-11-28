package com.example.pmo.shippingpolicy;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ClaimShippingReqDTO {
    @Getter
    private long orderId;   //주문 번호

    @Getter
    private long bundleShippingId;  //묶음 배송 번호

    @Getter
    private ClaimType claimType;  //클레임 타입

    @Getter
    private ClaimResponsibility claimResponsibility;  //클레임 책임

    @Getter
    private ArrayList<ClaimProductReqDTO> claimProductResDTOList;  //클레임 상세(주문상세번호, 클레임 수량) 리스트


    private ClaimShippingReqDTO() {}

    public ClaimShippingReqDTO(@NotNull final long orderId,
                               @NotNull final long bundleShippingId,
                               @NotNull final ClaimType claimType,
                               @NotNull final ClaimResponsibility claimResponsibility,
                               @NotNull final ArrayList<ClaimProductReqDTO> claimProductResDTOList) {
        this.orderId = orderId;
        this.bundleShippingId = bundleShippingId;
        this.claimType = claimType;
        this.claimResponsibility = claimResponsibility;
        this.claimProductResDTOList = claimProductResDTOList;
    }
}
