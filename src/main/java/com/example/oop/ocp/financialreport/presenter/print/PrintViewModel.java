package com.example.oop.ocp.financialreport.presenter.print;

public class PrintViewModel {
    private final String printContent;

    public PrintViewModel(String printContent) {
        this.printContent = printContent;
    }

    public String getPrintContent() {
        return printContent;
    }
}