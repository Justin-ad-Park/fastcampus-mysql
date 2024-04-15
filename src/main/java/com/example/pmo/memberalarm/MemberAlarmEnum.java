package com.example.pmo.memberalarm;

import com.example.pmo.memberalarm.alarmservice.AlarmService;
import com.example.pmo.memberalarm.alarmservice.GetAlarmParam;
import com.example.pmo.memberalarm.alarmservice.UserLevelAlarmService;
import com.example.pmo.memberalarm.alarmservice.WelcomeAlarmService;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum MemberAlarmEnum {

    USER_WELCOME("회원 {회원명}님 환영합니다.",
            Arrays.asList("{회원명}"),
            WelcomeAlarmService::new
             )
    , USER_CHANGE_LEVEL("회원 {회원명}님은 레벨{회원레벨}로 변경되었습니다.",
            Arrays.asList("{회원명}","{회원레벨}"),
            UserLevelAlarmService::new
    );

    private final String alarmTemplate;
    private final List<String> params;

    private final AlarmService alarmService;
    private final List<GetAlarmParam> getters;

    MemberAlarmEnum(String alarmTemplate, List<String> params, Supplier<AlarmService> alarmServiceCreator) {
        this.alarmTemplate = alarmTemplate;
        this.params = params;
        this.alarmService = alarmServiceCreator.get();
        this.getters = alarmService.getGetters();
    }

    public String getAlarmMessage(Long id) {
        // 값으로 치환활 변수 문자열 목록과 param 으로 받은 변수 목록의 길이가 다른 경우 null 반환
        if (params.size() != getters.size()) {
            new Exception("알람 메시지 처리용 파라미터 갯수가 맞지 않습니다.");
        }

        String modifiedMessage = alarmTemplate; // 수정된 메시지 : 최소 기본 상세 메시지로 세팅

        GetAlarmParam getAlarmParam;

        for (int i = 0; i < params.size(); i++) {

            getAlarmParam = getters.get(i);

            // 변수 문자열 목록을 param 으로 받은 값으로 치환
            modifiedMessage = modifiedMessage.replace(this.params.get(i), (String) getAlarmParam.getValue(id) );
        }

        return modifiedMessage;

    }
}
