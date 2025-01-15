package com.example.fivelines.gamever2.input;

import com.example.fivelines.gamever2.GameLogic;

public class Down implements InputAction {
    @Override
    public void execute(GameLogic gameLogic) {
        gameLogic.move(gameLogic.getPlayerX(), gameLogic.getPlayerY() + 1, 0, 1);
    }
}