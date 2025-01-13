package com.example.javaLang.generic.streamtest.groupBySum;

public class UpperBrandGroup {
    private String brandGroupId;
    private int redeemPoints;

    // Constructor
    public UpperBrandGroup(String brandGroupId, int redeemPoints) {
        this.brandGroupId = brandGroupId;
        this.redeemPoints = redeemPoints;
    }

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

    @Override
    public String toString() {
        return "UpperBrandGroup{" +
                "brandGroupId='" + brandGroupId + '\'' +
                ", redeemPoints=" + redeemPoints +
                '}';
    }
}