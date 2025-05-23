package com.example.oop.ocp.financialreport.database;

import com.example.oop.ocp.financialreport.interactor.FinancialEntities;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FinancialDatabase {
    private final ArrayList<FinancialEntities> database;

    public FinancialDatabase() {
        this.database = new ArrayList<>();

        // 고객 customer-001
        database.add(new FinancialEntities("customer-001", "예금", 1000000));
        database.add(new FinancialEntities("customer-001", "펀드", 250000));
        database.add(new FinancialEntities("customer-001", "주식", 3100000));

        // 고객 customer-002
        database.add(new FinancialEntities("customer-002", "예금", 500000));
        database.add(new FinancialEntities("customer-002", "보험", 150000));
        database.add(new FinancialEntities("customer-002", "주식", 2000000));
    }

    public ArrayList<FinancialEntities> query(String customerId) {
        return database.stream()
                .filter(entity -> entity.getCustomerId().equals(customerId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}