package com.example.oop.ocp.financialreport.database;

import com.example.oop.ocp.financialreport.interactor.FinancialDataGateway;
import com.example.oop.ocp.financialreport.interactor.FinancialEntities;

import java.util.ArrayList;

public class FinancialDataMapper implements FinancialDataGateway {
    private final FinancialDatabase db = new FinancialDatabase();

    @Override
    public ArrayList<FinancialEntities> loadFinancialData(String customerId) {
        return db.query(customerId);
    }
}