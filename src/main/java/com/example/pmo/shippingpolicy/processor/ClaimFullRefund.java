package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.ClaimShippingReqDTO;
import com.example.pmo.shippingpolicy.ClaimShippingResDTO;
import org.jetbrains.annotations.NotNull;

/**
 * 클레임에서 전액 환불하는 케이스의 배송비 계산기
 * 대상 클레임 타입 :
 *  - 전체취소(판매자)
 *  - 전체취소(구매자)
 *  - 전체반품(판매자)
 */
class ClaimFullRefund implements ClaimShippingProcessor {

    @Override
    public ClaimShippingResDTO calculate(@NotNull ClaimShippingReqDTO claimShippingReqDTO) throws Exception {
        return getTestResDto();
    }
}
