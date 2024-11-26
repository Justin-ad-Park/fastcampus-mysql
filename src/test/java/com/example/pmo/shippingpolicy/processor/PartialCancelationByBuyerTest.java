package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.*;
import com.example.pmo.shippingpolicy.processor.implement.PartialCancelationByBuyer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PartialCancelationByBuyerTest {

    @Test
    void calculate_요청파라미터는notNull() {
        // given
        PartialCancelationByBuyer partialCancelationByBuyer = new PartialCancelationByBuyer();

        // when

        // then
        assertThrows(Exception.class, () -> partialCancelationByBuyer.calculate(null));
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
        assertNotNull(claimShippingResDTO);
    }
}