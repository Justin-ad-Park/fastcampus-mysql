package com.example.oop.ocp.financialreport.presenter.print;

import com.example.oop.ocp.financialreport.interactor.FinancialEntities;
import com.example.oop.ocp.financialreport.interactor.FinancialReportResponse;
import com.example.oop.ocp.financialreport.presenter.FinancialReportPresenter;

public class PrintPresenter implements FinancialReportPresenter {
    private final PrintView view;

    public PrintPresenter(PrintView view) {
        this.view = view;
    }

    @Override
    public void present(FinancialReportResponse response) {
        StringBuilder sb = new StringBuilder("📄 인쇄용 보고서:\n");
        for (FinancialEntities entity : response.getEntities()) {
            sb.append("* ").append(entity.getAssetName())
                    .append(": ").append(entity.getBalance())
                    .append("원\n");
        }
        PrintViewModel vm = new PrintViewModel(sb.toString());
        view.display(vm);
    }
}