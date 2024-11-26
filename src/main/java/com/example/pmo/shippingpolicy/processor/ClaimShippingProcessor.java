package com.example.pmo.shippingpolicy.processor;

public interface ClaimShippingProcessor {

        /**
        * 배송비 정책을 적용하여 배송비를 계산한다.
        */
        ClaimShippingResDTO calculate(ClaimShippingReqDTO claimShippingReqDTO) throws Exception;

        // 테스트 케이스용 임시 메소드
        default ClaimShippingResDTO getTestResDto() {
                ClaimShippingResDTO resDto = new ClaimShippingResDTO(1, 101, 1);
                return resDto;
        }
}
