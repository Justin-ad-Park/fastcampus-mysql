package com.example.pmo.poormvcmodel;

public class NoSufficientBalanceException extends Throwable {
    public NoSufficientBalanceException(String message) {
        super(message);
    }
}
