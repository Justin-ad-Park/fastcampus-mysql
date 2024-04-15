package com.example.pmo.memberalarm4.domain.userservice;

import java.util.HashMap;
public class UserService {
        private HashMap<Long, UserDto> userList = new HashMap<>();

    public UserService() {
        userList.put(1L, new UserDto(1L, "홍길동"));
        userList.put(2L, new UserDto(2L, "김유신"));
        userList.put(2L, new UserDto(2L, "풀무원"));
    }

    public UserDto getUser(long userId) {
        return userList.get(userId);
    }

}