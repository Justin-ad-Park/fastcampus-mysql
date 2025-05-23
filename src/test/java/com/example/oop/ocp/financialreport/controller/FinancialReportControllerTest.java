package com.example.oop.ocp.financialreport.controller;

import com.example.oop.ocp.financialreport.database.FinancialDataMapper;
import com.example.oop.ocp.financialreport.interactor.FinancialReportGenerator;
import com.example.oop.ocp.financialreport.interactor.FinancialReportRequester;
import com.example.oop.ocp.financialreport.presenter.FinancialReportPresenter;
import com.example.oop.ocp.financialreport.presenter.print.PrintPresenter;
import com.example.oop.ocp.financialreport.presenter.screen.ScreenPresenter;
import com.example.oop.ocp.financialreport.view.PdfView;
import com.example.oop.ocp.financialreport.view.WebView;
import org.junit.jupiter.api.Test;

import java.util.List;
class FinancialReportControllerTest {

    @Test
    void test() {
        List<FinancialReportPresenter> presenters = List.of(new ScreenPresenter(new WebView()), new PrintPresenter(new PdfView()));
        FinancialReportRequester requester = new FinancialReportGenerator(new FinancialDataMapper()); // ✅ 인터페이스로 받음
        FinancialReportController controller = new FinancialReportController(presenters, requester);

        controller.handle("customer-002");
    }

}
