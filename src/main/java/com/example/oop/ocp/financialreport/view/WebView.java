package com.example.oop.ocp.financialreport.view;

import com.example.oop.ocp.financialreport.presenter.screen.ScreenView;
import com.example.oop.ocp.financialreport.presenter.screen.ScreenViewModel;

public class WebView implements ScreenView {
    @Override
    public void display(ScreenViewModel vm) {
        System.out.println("🌐 Web 화면에 출력: " + vm.formatted);
    }
}