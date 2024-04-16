package com.example.pmo.memberalarm4;

import com.example.pmo.memberalarm4.alarmservice.AlarmService;
import com.example.pmo.memberalarm4.alarmservice.GradeAlarmService;
import com.example.pmo.memberalarm4.alarmservice.WelcomeAlarmService;

import java.util.function.Supplier;

public enum MemberAlarmEnum4 {

    USER_WELCOME("회원가입", "USER_REG", "1", WelcomeAlarmService::new)
    , USER_CHANGE_LEVEL("회원 레벨 변경", "USER_LEVEL", "2", GradeAlarmService::new);

    private final String alarmName;

    private final String compID;

    private final String compVer;
    private final AlarmService alarmService;

    public String getCompVer() {
        return this.compVer;
    }

    public String getCompID() {
        return this.compID;
    }

    MemberAlarmEnum4(String alarmName, String compID, String compVer, Supplier<AlarmService> alarmServiceCreator) {
        this.alarmName = alarmName;
        this.compID = compID;
        this.compVer = compVer;
        this.alarmService = alarmServiceCreator.get();
        this.alarmService.setEnum(this);
    }

    public String getAlarmMessage(Long id) {
        return alarmService.getAlarmMessage(id);
    }
}
