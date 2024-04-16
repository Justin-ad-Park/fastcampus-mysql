package com.example.pmo.memberalarm4.alarmservice;

import com.example.pmo.memberalarm4.domain.gradeservice.GradeDto;
import com.example.pmo.memberalarm4.domain.gradeservice.GradeService;

public class GradeAlarmService extends AlarmService {
    private static final String alarmTemplate = """
                {"UserNM": "{회원명}"},
                {"UserLevel": "{회원레벨}"}
            """;

    private GradeService gradeService;
    private GradeDto gradeDto;

    public GradeAlarmService() {
        gradeService = new GradeService();
    }

    @Override
    protected String templateParams() {
        return alarmTemplate;
    }

    @Override
    protected void setParamsMapper() {
        paramsMapper.put("{회원명}", gradeDto.getUserName);
        paramsMapper.put("{회원레벨}", gradeDto.getUserLevel);
    }

    @Override
    protected void loadData(Long id) {
        gradeDto = gradeService.getGradeInfo(id);
    }
}