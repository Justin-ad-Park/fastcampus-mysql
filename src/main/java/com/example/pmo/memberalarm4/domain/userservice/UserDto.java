package com.example.pmo.memberalarm4.domain.userservice;

import com.example.pmo.memberalarm4.alarmservice.GetAlarmParam;

public class UserDto {
    private long userId;

    private String userName;

    public UserDto(final long userId, final String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public GetAlarmParam<String> getUserName = () -> userName;

}
