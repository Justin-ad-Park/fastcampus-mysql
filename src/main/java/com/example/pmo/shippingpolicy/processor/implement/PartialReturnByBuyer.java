package com.example.pmo.shippingpolicy.processor.implement;

import com.example.pmo.shippingpolicy.processor.ClaimShippingProcessor;
import com.example.pmo.shippingpolicy.processor.ClaimShippingReqDTO;
import com.example.pmo.shippingpolicy.processor.ClaimShippingResDTO;
import org.jetbrains.annotations.NotNull;

/**
 * 부분 취소(판매자)의 배송비 계산기
 */
public class PartialReturnByBuyer implements ClaimShippingProcessor {

    @Override
    public ClaimShippingResDTO calculate(@NotNull ClaimShippingReqDTO claimShippingReqDTO) throws Exception {
        return getTestResDto();
    }
}
