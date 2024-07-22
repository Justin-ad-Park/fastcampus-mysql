package com.example.pmo.memberalarm3.alarmservice;

import com.example.pmo.memberalarm3.domain.userservice.UserDto;
import com.example.pmo.memberalarm3.domain.userservice.UserService;

import java.util.Arrays;
import java.util.List;

public class WelcomeAlarmService extends AlarmService {

    private static final String alarmTemplate = "[가입인사]회원 {회원명}님 가입을 환영합니다.";

    private UserService userService;
    private UserDto userDto;

    public WelcomeAlarmService() {
        userService = new UserService();
    }

    @Override
    public String messageTemplate() {
        return alarmTemplate;
    }

    @Override
    public List<GetAlarmParam> getGetters() {
        return Arrays.asList(userDto.getUserName);
    }

    @Override
    public List<String> getParams() {
        return Arrays.asList("{회원명}");
    }

    @Override
    protected void loadData(Long id) {
        userDto = userService.getUser(id);
    }


}
