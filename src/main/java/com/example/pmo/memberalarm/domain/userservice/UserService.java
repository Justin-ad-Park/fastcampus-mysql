package com.example.pmo.memberalarm.domain.userservice;

import com.example.pmo.memberalarm.alarmservice.GetAlarmParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
        private HashMap<Long, UserVo> userList = new HashMap<>();

    public UserService() {
        userList.put(1L, new UserVo(1L, "홍길동", 1));
        userList.put(2L, new UserVo(2L, "김유신", 2));
        userList.put(2L, new UserVo(2L, "풀무원", 3));
    }

    private String getUser(long userId) {
        return userList.get(userId).getUserName();
    }

    private String getUserLevel(long userId) {
        return String.valueOf(userList.get(userId).getUserLevel());
    }


    public GetAlarmParam<Long, String> getUserName = (userId) -> this.getUser(userId);

    public GetAlarmParam<Long, String> getUserLevel = (userId) -> this.getUserLevel(userId);

}