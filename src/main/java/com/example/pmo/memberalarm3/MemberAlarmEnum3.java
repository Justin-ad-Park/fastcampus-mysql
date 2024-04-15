package com.example.pmo.memberalarm3;

import com.example.pmo.memberalarm3.alarmservice.AlarmService;
import com.example.pmo.memberalarm3.alarmservice.GradeAlarmService;
import com.example.pmo.memberalarm3.alarmservice.WelcomeAlarmService;

import java.util.function.Supplier;

public enum MemberAlarmEnum3 {

    USER_WELCOME("회원가입",
            WelcomeAlarmService::new
             )
    , USER_CHANGE_LEVEL("회원 레벨 변경",
            GradeAlarmService::new
    );

    private final String alarmName;
    private final AlarmService alarmService;

    MemberAlarmEnum3(String alarmName, Supplier<AlarmService> alarmServiceCreator) {
        this.alarmName = alarmName;
        this.alarmService = alarmServiceCreator.get();
    }

    public String getAlarmMessage(Long id) {
        return alarmService.getAlarmMessage(id);
    }
}
