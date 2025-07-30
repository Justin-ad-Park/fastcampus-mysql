package com.example.wumpusgame.architecture02.application;

import com.example.wumpusgame.architecture02.language.MessageKey;

public class MoveResult {
    private final MessageKey status;

    public MoveResult(MessageKey status) {
        this.status = status;
    }

    public MessageKey getStatus() { return status; }
}