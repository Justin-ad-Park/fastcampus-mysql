package com.example.fivelines.gamever1_1.inputv2;

import com.example.fivelines.gamever1_1.PlayerControl;

public class Down implements Input {
    @Override
    public void handle(PlayerControl pc) {
        pc.move(0, 1);
    }
}