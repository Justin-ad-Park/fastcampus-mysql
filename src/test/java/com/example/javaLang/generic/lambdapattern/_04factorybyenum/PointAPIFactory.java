package com.example.javaLang.generic.lambdapattern._04factorybyenum;

public class PointAPIFactory {
    public static PointAPI getAPIInstance(String pg) {
        return PointPG.valueOf(pg).getInstance();
    }
}
