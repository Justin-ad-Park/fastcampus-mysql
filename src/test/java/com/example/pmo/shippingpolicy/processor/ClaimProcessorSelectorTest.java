package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ClaimProcessorSelectorTest {

    @Test
    void getInstance_객체생성테스트() throws Exception {
        // given
        ClaimShippingProcessor claimProcessor = ClaimProcessorSelector.getInstance(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY);

        // then
        Assertions.assertNotNull(claimProcessor);
    }

    @Test
    void getInstance_리턴값테스트() throws Exception {
        // given
        ClaimShippingProcessor claimProcessor = ClaimProcessorSelector.getInstance(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY);

        // when
        ArrayList<ClaimProductReqDTO> claimProductReqDTOList = new ArrayList<>();
        claimProductReqDTOList.add(new ClaimProductReqDTO(1, 1));

        ClaimShippingReqDTO claimShippingReqDTO = new ClaimShippingReqDTO(1, 1,
                ClaimType.PARTIAL_CANCELLATION,
                ClaimResponsibility.BUYER_RESPONSIBILITY,
                null);

        // then
        ClaimShippingResDTO resDTO = claimProcessor.calculate(claimShippingReqDTO);
        Assertions.assertNotNull(resDTO);
    }
}