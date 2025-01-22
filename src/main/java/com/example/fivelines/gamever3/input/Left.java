package com.example.fivelines.gamever3.input;

import com.example.fivelines.gamever3.gamelogic.PlayerControl;

public class Left implements InputAction {
    @Override
    public void execute(PlayerControl gameLogic) {
        gameLogic.movePlayer( -1, 0);
    }
}