package com.example.oop.ocp.financialreport.interactor;

import java.util.List;

public interface FinancialDataGateway {
    List<FinancialEntities> loadFinancialData(String customerId);
}