package com.example.javaLang.generic.lambdapattern.factory2;

public class PGA_PointAPI implements PointAPI {
    @Override
    public int getPoint(int memberId) {
        System.out.println("A-PG사 조회");
        return 100;
    }

    @Override
    public int deposit(int memberId, int point) {
        System.out.println("A-PG사 차감");
        return 1234567;
    }

    @Override
    public boolean cancel(int transactionId, int point) {
        System.out.println("A-PG사 취소 성공");
        return true;
    }
}
