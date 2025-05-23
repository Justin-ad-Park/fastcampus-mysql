package com.example.oop.ocp.financialreport.interactor;

public class FinancialReportRequest {
    private final String customerId;

    public FinancialReportRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}