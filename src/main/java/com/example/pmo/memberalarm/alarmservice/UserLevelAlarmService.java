package com.example.pmo.memberalarm.alarmservice;

import com.example.pmo.memberalarm.domain.userservice.UserService;

import java.util.Arrays;
import java.util.List;

public class UserLevelAlarmService  implements AlarmService {

    private UserService userService;

    public UserLevelAlarmService() {
        userService = new UserService();
    }

    public List<GetAlarmParam> getGetters() {
        return Arrays.asList(userService.getUserName, userService.getUserLevel);
    }

}
