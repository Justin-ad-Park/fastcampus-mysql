package com.example.fivelines.gamever3.input;

import com.example.fivelines.gamever3.gamelogic.PlayerControl;

// Right.java
public class Right implements InputAction {
    @Override
    public void execute(PlayerControl gameLogic) {
        gameLogic.movePlayer( 1, 0);
    }
}