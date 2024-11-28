package com.example.pmo.shippingpolicy;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Builder
public class ClaimShippingResDTO {
    @Getter
    private long refundAdditionalShippingPrice; //환불/부과 배송비

    @Getter
    private int afterShippingPolicyId;

    @Getter
    private long afterShippingPrice; //배송비

    private ClaimShippingResDTO() {
    }

    public ClaimShippingResDTO(@NotNull final long refundAdditionalShippingPrice, @NotNull final int afterShippingPolicyId, @NotNull final long afterShippingPrice) {
        this.refundAdditionalShippingPrice = refundAdditionalShippingPrice;
        this.afterShippingPolicyId = afterShippingPolicyId;
        this.afterShippingPrice = afterShippingPrice;
    }

}
