package com.example.fivelines.gamever2.input;

import com.example.fivelines.gamever2.PlayerControl;

// Right.java
public class Right implements InputAction {
    @Override
    public void execute(PlayerControl gameLogic) {
        gameLogic.movePlayer( 1, 0);
    }
}