package com.example.pmo.memberalarm3.alarmservice;

import com.example.pmo.memberalarm3.domain.gradeservice.GradeDto;
import com.example.pmo.memberalarm3.domain.gradeservice.GradeService;

import java.util.Arrays;
import java.util.List;

public class GradeAlarmService extends AlarmService {

    private static final String alarmTemplate = "[등급변경]{회원명}님은 레벨{회원레벨}로 회원 등급이 변경되었습니다.";

    private GradeService gradeService;
    private GradeDto gradeDto;

    public GradeAlarmService() {
        gradeService = new GradeService();
    }

    @Override
    protected String messageTemplate() {
        return alarmTemplate;
    }

    @Override
    protected List<String> getParams() {
        return Arrays.asList("{회원명}", "{회원레벨}");
    }

    @Override
    protected List<GetAlarmParam> getGetters() {
        return Arrays.asList(gradeDto.getUserName, gradeDto.getUserLevel);
    }

    @Override
    protected void loadData(Long id) {
        gradeDto = gradeService.getGradeInfo(id);
    }
}