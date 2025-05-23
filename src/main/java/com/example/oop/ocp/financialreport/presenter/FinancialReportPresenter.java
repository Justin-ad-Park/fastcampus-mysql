package com.example.oop.ocp.financialreport.presenter;

import com.example.oop.ocp.financialreport.interactor.FinancialReportResponse;


public interface FinancialReportPresenter {
    void present(FinancialReportResponse response);
}