package com.example.pmo.memberalarm4.domain.gradeservice;


import java.util.function.Supplier;

public class GradeDto {
    private long userId;

    private String userName;

    private int userLevel;

    public GradeDto(final long userId, final String userName, final int userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userLevel = userLevel;
    }

    public Supplier<String> getUserName = () -> userName;

    public Supplier<String> getUserLevel = () -> String.valueOf(userLevel);
}
