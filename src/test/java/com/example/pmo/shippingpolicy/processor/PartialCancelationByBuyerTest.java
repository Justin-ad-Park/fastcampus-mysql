package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.*;
import com.example.pmo.shippingpolicy.processor.implement.PartialCancelationByBuyer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PartialCancelationByBuyerTest {

    @Test
    void calculate_응답객체_생성여부() throws Exception {
        // given
        PartialCancelationByBuyer partialCancelationByBuyer = new PartialCancelationByBuyer();

        // when

        // then
        ClaimShippingResDTO resDto = partialCancelationByBuyer.calculate(null);

        Assertions.assertNotNull(resDto);
    }

    @Test
    void calculate_반환값NotNull() throws Exception {
        // given
        PartialCancelationByBuyer partialCancelationByBuyerMock = mock(PartialCancelationByBuyer.class);

        // when
        ArrayList<ClaimProductReqDTO> claimProductReqDTOList = new ArrayList<>();
        claimProductReqDTOList.add(new ClaimProductReqDTO(1, 1));

        ClaimShippingReqDTO claimShippingReqDTO = new ClaimShippingReqDTO(1, 1,
                ClaimType.PARTIAL_CANCELLATION,
                ClaimResponsibility.BUYER_RESPONSIBILITY,
                null);

        given(partialCancelationByBuyerMock.calculate(claimShippingReqDTO))
                .willReturn(new ClaimShippingResDTO(1, 1, 1));

        ClaimShippingResDTO claimShippingResDTO = partialCancelationByBuyerMock.calculate(claimShippingReqDTO);

        // then
        Assertions.assertNotNull(claimShippingResDTO);
    }
}