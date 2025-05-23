package com.example.oop.ocp.financialreport.controller;

import com.example.oop.ocp.financialreport.interactor.FinancialReportRequest;
import com.example.oop.ocp.financialreport.interactor.FinancialReportRequester;
import com.example.oop.ocp.financialreport.interactor.FinancialReportResponse;
import com.example.oop.ocp.financialreport.presenter.FinancialReportPresenter;

import java.util.List;


public class FinancialReportController {
    private final List<FinancialReportPresenter> presenters;
    private final FinancialReportRequester requester;

    public FinancialReportController(List<FinancialReportPresenter> presenters,
                                     FinancialReportRequester requester) {
        this.presenters = presenters;
        this.requester = requester;
    }

    public void handle(String customerId) {
        FinancialReportRequest request = new FinancialReportRequest(customerId);
        FinancialReportResponse response = requester.requestReport(request);
        for (FinancialReportPresenter presenter : presenters) {
            presenter.present(response);
        }
    }
}