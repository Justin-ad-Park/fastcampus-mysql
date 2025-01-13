package com.example.javaLang.generic.streamtest.groupBySum;

import java.util.List;

public class BrandGroupsReqDto {
    private String mallId;
    private String orderId;
    private String orderDt;
    private List<BrandGroup> brandGroups;

    // Getters and Setters
    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(String orderDt) {
        this.orderDt = orderDt;
    }

    public List<BrandGroup> getBrandGroups() {
        return brandGroups;
    }

    public void setBrandGroups(List<BrandGroup> brandGroups) {
        this.brandGroups = brandGroups;
    }

    public static class BrandGroup {
        private String brandGroupId;
        private int redeemPoints;

        // Getters and Setters
        public String getBrandGroupId() {
            return brandGroupId;
        }

        public void setBrandGroupId(String brandGroupId) {
            this.brandGroupId = brandGroupId;
        }

        public int getRedeemPoints() {
            return redeemPoints;
        }

        public void setRedeemPoints(int redeemPoints) {
            this.redeemPoints = redeemPoints;
        }
    }
}
