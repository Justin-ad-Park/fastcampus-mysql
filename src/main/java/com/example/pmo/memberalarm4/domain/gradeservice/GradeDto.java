package com.example.pmo.memberalarm4.domain.gradeservice;

import com.example.pmo.memberalarm4.alarmservice.GetAlarmParam;

public class GradeDto {
    private long userId;

    private String userName;

    private int userLevel;

    public GradeDto(final long userId, final String userName, final int userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userLevel = userLevel;
    }

    public GetAlarmParam<String> getUserName = () -> userName;

    public GetAlarmParam<String> getUserLevel = () -> String.valueOf(userLevel);
}
