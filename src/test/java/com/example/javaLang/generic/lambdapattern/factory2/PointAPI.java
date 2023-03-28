package com.example.javaLang.generic.lambdapattern.factory2;

public interface PointAPI {
    int getPoint(int memberId);
    int deposit(int memberId, int point);
    boolean cancel(int transactionId, int point);
}
