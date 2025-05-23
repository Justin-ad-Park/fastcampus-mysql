package com.example.oop.ocp.financialreport.presenter.screen;

import com.example.oop.ocp.financialreport.interactor.FinancialEntities;
import com.example.oop.ocp.financialreport.interactor.FinancialReportResponse;
import com.example.oop.ocp.financialreport.presenter.FinancialReportPresenter;


public class ScreenPresenter implements FinancialReportPresenter {
    private final ScreenView view;

    public ScreenPresenter(ScreenView view) {
        this.view = view;
    }

    @Override
    public void present(FinancialReportResponse response) {
        StringBuilder sb = new StringBuilder("💻 화면 출력:\n");
        for (FinancialEntities entity : response.getEntities()) {
            sb.append("- ").append(entity.getAssetName())
                    .append(": ").append(entity.getBalance())
                    .append("원\n");
        }
        ScreenViewModel vm = new ScreenViewModel(sb.toString());
        view.display(vm);
    }
}