package com.example.pmo.shippingpolicy;

import com.example.pmo.shippingpolicy.processor.ClaimProductReqDTO;
import com.example.pmo.shippingpolicy.processor.ClaimShippingProcessor;
import com.example.pmo.shippingpolicy.processor.ClaimShippingReqDTO;
import com.example.pmo.shippingpolicy.processor.ClaimShippingResDTO;
import com.example.pmo.shippingpolicy.processor.implement.PartialCancelationByBuyer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ClaimProcessorSelectorTest {

    @Test
    void getInstance_타입체크() throws Exception {
        // given
        ClaimShippingProcessor claimProcessor = ClaimProcessorSelector.getInstance(ClaimType.PARTIAL_CANCELLATION, ClaimResponsibility.BUYER_RESPONSIBILITY);

        // then
        Assertions.assertTrue(claimProcessor instanceof PartialCancelationByBuyer);
    }

    @Test
    void getInstance() throws Exception {
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