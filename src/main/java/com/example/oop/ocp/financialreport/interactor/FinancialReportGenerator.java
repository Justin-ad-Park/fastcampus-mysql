package com.example.oop.ocp.financialreport.interactor;

import java.util.List;


public class FinancialReportGenerator implements FinancialReportRequester {
    private final FinancialDataGateway dataGateway;

    public FinancialReportGenerator(FinancialDataGateway dataGateway) {
        this.dataGateway = dataGateway;
    }

    @Override
    public FinancialReportResponse requestReport(FinancialReportRequest request) {
        List<FinancialEntities> data = dataGateway.loadFinancialData(request.getCustomerId());
        return new FinancialReportResponse(data);
    }
}