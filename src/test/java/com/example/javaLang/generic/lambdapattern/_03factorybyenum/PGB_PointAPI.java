package com.example.javaLang.generic.lambdapattern._03factorybyenum;

public class PGB_PointAPI implements PointAPI {
    @Override
    public int getPoint(int memberId) {
        System.out.println("B-PG사 조회");
        return 9999;
    }

    @Override
    public int deposit(int memberId, int point) {
        System.out.println("B-PG사 차감");
        return 9876543;
    }

    @Override
    public boolean cancel(int transactionId, int point) {
        System.out.println("BPG사 취소 성공");
        return true;
    }
}
