package com.example.wumpusgame.architecture02.application;

import com.example.wumpusgame.architecture02.domain.GameRules;
import com.example.wumpusgame.architecture02.language.MessageKey;

import java.util.List;

public class GameEngine {
    private final GameRules rules;

    public GameEngine(GameRules rules) {
        this.rules = rules;
    }

    public int getPlayerRoom() {
        return rules.getPlayerRoom();
    }

    public int[] getAdjacentRooms() {
        return rules.getAdjacentRooms();
    }

    public List<MessageKey> getSenses() {
        return rules.sense();
    }

    public MoveResult movePlayer(int roomId) {
        return rules.move(roomId);
    }

    public MoveResult shootArrow(int roomId) {
        return rules.shoot(roomId);
    }
}