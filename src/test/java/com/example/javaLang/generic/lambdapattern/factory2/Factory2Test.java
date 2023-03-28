package com.example.javaLang.generic.lambdapattern.factory2;

import org.junit.jupiter.api.Test;

public class Factory2Test {
    @Test
    void Test() {
        PointAPI pointAPI = PointAPIFactory.getAPIInstance("PGA");

        int remainPoint = pointAPI.getPoint(1);
        int transactionID = pointAPI.deposit(1, 100);
        boolean isSuccess = pointAPI.cancel(transactionID, 100);

        PointAPI pointAPI2 = PointAPIFactory.getAPIInstance("PGB");

        remainPoint = pointAPI2.getPoint(1);
        transactionID = pointAPI2.deposit(1, 200);
        isSuccess = pointAPI2.cancel(transactionID, 200);
    }
}
