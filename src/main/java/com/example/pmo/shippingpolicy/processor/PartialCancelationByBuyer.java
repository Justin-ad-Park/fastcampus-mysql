package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.ClaimShippingReqDTO;
import com.example.pmo.shippingpolicy.ClaimShippingResDTO;
import org.jetbrains.annotations.NotNull;

/**
 * 부분 취소(구매자)의 배송비 계산기
 */
class PartialCancelationByBuyer implements ClaimShippingProcessor {

    @Override
    public ClaimShippingResDTO calculate(@NotNull ClaimShippingReqDTO claimShippingReqDTO) throws Exception {
        return getTestResDto();
    }
}
