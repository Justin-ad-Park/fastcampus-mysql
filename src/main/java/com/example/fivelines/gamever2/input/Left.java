package com.example.fivelines.gamever2.input;

import com.example.fivelines.gamever2.PlayerControl;

public class Left implements InputAction {
    @Override
    public void execute(PlayerControl gameLogic) {
        gameLogic.movePlayer( -1, 0);
    }
}