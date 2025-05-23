package com.example.oop.ocp.financialreport.interactor;

public class FinancialEntities {
    private final String customerId;
    private final String assetName;
    private final double balance;

    public FinancialEntities(String customerId, String assetName, double balance) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAssetName() {
        return assetName;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return assetName + ": " + balance + "원";
    }
}