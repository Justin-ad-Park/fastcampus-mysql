package com.example.pmo.memberalarm.alarmservice;

@FunctionalInterface
public interface GetAlarmParam<T, R> {
    R getValue(T t);

}