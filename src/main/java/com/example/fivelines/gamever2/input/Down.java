package com.example.fivelines.gamever2.input;

import com.example.fivelines.gamever2.PlayerControl;

public class Down implements InputAction {
    @Override
    public void execute(PlayerControl gameLogic) {
        gameLogic.movePlayer(0, 1);
    }
}