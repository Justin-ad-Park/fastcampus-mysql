package com.example.javaLang.generic.lambdapattern.factory2;

public class PointAPIFactory {
    public static PointAPI getAPIInstance(String pg) {
        return PointPG.valueOf(pg).getInstance();
    }
}
