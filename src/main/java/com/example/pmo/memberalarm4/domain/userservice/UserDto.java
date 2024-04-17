package com.example.pmo.memberalarm4.domain.userservice;

import java.util.function.Supplier;

public class UserDto {
    private long userId;

    private String userName;

    public UserDto(final long userId, final String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Supplier<String> getUserName = () -> userName;

}
