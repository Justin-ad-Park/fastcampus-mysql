package com.example.pmo.memberalarm.domain.userservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private long userId;

    private String userName;

    private int userLevel;

    public UserVo(final long userId, final String userName, final int userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userLevel = userLevel;
    }

}
