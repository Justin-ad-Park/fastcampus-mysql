package com.example.pmo.memberalarm4.domain.gradeservice;

import java.util.HashMap;

public class GradeService {
        private HashMap<Long, GradeDto> userList = new HashMap<>();

    public GradeService() {
        userList.put(1L, new GradeDto(1L, "홍길동", 1));
        userList.put(2L, new GradeDto(2L, "김유신", 2));
        userList.put(2L, new GradeDto(2L, "풀무원", 3));
    }

    public GradeDto getGradeInfo(long userId) {
        return userList.get(userId);
    }


}