package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ClaimShippingProcessorFactoryTest {

    @Test
    void getCalculator() throws Exception {

        // given
        ClaimShippingProcessor claimShippingProcessor = ClaimShippingProcessorFactory.getCalculator(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY);

        // when
        ArrayList<ClaimProductReqDTO> claimProductReqDTOList = new ArrayList<>();
        claimProductReqDTOList.add(new ClaimProductReqDTO(1, 1));

        ClaimShippingReqDTO claimShippingReqDTO = new ClaimShippingReqDTO(1, 1,
                ClaimType.PARTIAL_CANCELLATION,
                ClaimResponsibility.BUYER_RESPONSIBILITY,
                claimProductReqDTOList);

        //given(claimShippingCalculator.calculate(claimShippingReqDTO)).willReturn(new ClaimShippingResDTO(1, 1, 1));

        ClaimShippingResDTO claimShippingResDTO = claimShippingProcessor.calculate(claimShippingReqDTO);

        // then
        Assertions.assertNotNull(claimShippingResDTO);

    }
}