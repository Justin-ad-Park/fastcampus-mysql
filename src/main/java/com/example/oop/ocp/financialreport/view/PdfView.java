package com.example.oop.ocp.financialreport.view;

import com.example.oop.ocp.financialreport.presenter.print.PrintView;
import com.example.oop.ocp.financialreport.presenter.print.PrintViewModel;

public class PdfView implements PrintView {
    @Override
    public void display(PrintViewModel viewModel) {
        System.out.println("🖨️ PDF 출력:\n" + viewModel.getPrintContent());
    }
}