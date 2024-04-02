package com.example.javaLang.generic.lambdapattern._03factorybyenum;

public class PointAPIFactory {
    public static PointAPI getAPIInstance(String pg) {
        return PointPG.valueOf(pg).getInstance();
    }
}
