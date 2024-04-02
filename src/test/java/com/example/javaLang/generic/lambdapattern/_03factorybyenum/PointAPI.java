package com.example.javaLang.generic.lambdapattern._03factorybyenum;

public interface PointAPI {
    int getPoint(int memberId);
    int deposit(int memberId, int point);
    boolean cancel(int transactionId, int point);
}
