package com.example.pmo.memberalarm.alarmservice;

import com.example.pmo.memberalarm.domain.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WelcomeAlarmService implements AlarmService {

    private UserService userService;

    public WelcomeAlarmService() {
        userService = new UserService();
    }

    public List<GetAlarmParam> getGetters() {
        return Arrays.asList(userService.getUserName);
    }

}
