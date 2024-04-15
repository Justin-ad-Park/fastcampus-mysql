package com.example.pmo.memberalarm4.alarmservice;

import com.example.pmo.memberalarm4.domain.userservice.UserDto;
import com.example.pmo.memberalarm4.domain.userservice.UserService;

public class WelcomeAlarmService extends AlarmService {

    private static final String alarmTemplate = "[가입인사]회원 {회원명}님 가입을 환영합니다.";

    private UserService userService;
    private UserDto userDto;

    public WelcomeAlarmService() {
        userService = new UserService();
    }

    @Override
    protected String messageTemplate() {
        return alarmTemplate;
    }

    @Override
    protected void setParamsMapper() {
        paramsMapper.put("{회원명}", userDto.getUserName);
    }

    @Override
    protected void loadData(Long id) {
        userDto = userService.getUser(id);
    }

}
