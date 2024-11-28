package com.example.pmo.shippingpolicy.processor;

import com.example.pmo.shippingpolicy.ClaimResponsibility;
import com.example.pmo.shippingpolicy.ClaimType;
import com.example.pmo.shippingpolicy.processor.impl.ClaimFullRefund;
import com.example.pmo.shippingpolicy.processor.impl.PartialCancelationByBuyer;
import com.example.pmo.shippingpolicy.processor.impl.PartialClaimBySeller;

/**
 * 배송비 처리기 팩토리
 * @deprecated
 * @see ClaimProcessorSelector
 *  일반적인 팩토리 패턴을 사용하는 것보다 ClaimProcessorDistributor를 사용하는 것이 더 직관적이고,
 *  실수를 줄일 수 있어서 이 클래스는 사용하지 않기로 함.
 */
@Deprecated
public class ClaimShippingProcessorFactory {

    protected static ClaimShippingProcessor getCalculator(final ClaimType claimType, final ClaimResponsibility claimResponsibility) {
        switch (claimType) {
            case PARTIAL_CANCELLATION:
                if(claimResponsibility.equals(ClaimResponsibility.BUYER_RESPONSIBILITY))
                    return new PartialCancelationByBuyer();
                else
                    return new PartialClaimBySeller();
            case FULL_CANCELLATION:
                return new ClaimFullRefund();
            case PARTIAL_RETURN:

            case FULL_RETURN:
                if(claimResponsibility.equals(ClaimResponsibility.BUYER_RESPONSIBILITY))
                    return null;
                else
                    return new ClaimFullRefund();
        }

        throw new IllegalArgumentException("Do not support : " + claimType);
    }

}
