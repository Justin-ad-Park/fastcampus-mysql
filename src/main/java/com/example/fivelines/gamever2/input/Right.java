package com.example.fivelines.gamever2.input;

import com.example.fivelines.gamever2.GameLogic;

// Right.java
public class Right implements InputAction {
    @Override
    public void execute(GameLogic gameLogic) {
        gameLogic.move(gameLogic.getPlayerX() + 1, gameLogic.getPlayerY(), 1, 0);
    }
}