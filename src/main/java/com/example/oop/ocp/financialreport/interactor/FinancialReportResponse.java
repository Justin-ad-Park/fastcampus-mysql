package com.example.oop.ocp.financialreport.interactor;

import java.util.List;

public class FinancialReportResponse {
    private final List<FinancialEntities> entities;

    public FinancialReportResponse(List<FinancialEntities> entities) {
        this.entities = entities;
    }

    public List<FinancialEntities> getEntities() {
        return entities;
    }
}