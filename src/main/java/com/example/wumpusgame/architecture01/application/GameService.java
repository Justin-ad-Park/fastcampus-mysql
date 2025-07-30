package com.example.wumpusgame.architecture01.application;

import com.example.wumpusgame.architecture01.domain.WumpusGame;
import java.util.List;

/**
 * Application Layer (게임 진행 유스케이스)
 */
public class GameService {
    private final WumpusGame game;

    public GameService(WumpusGame game) {
        this.game = game;
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("You are in room ").append(game.getPlayerRoom()).append("\n");
        sb.append("Tunnels lead to: ");
        for (int r : game.getAdjacentRooms()) sb.append(r).append(" ");
        sb.append("\n");
        List<String> clues = game.sense();
        for (String clue : clues) sb.append(clue).append("\n");
        return sb.toString();
    }

    public String movePlayer(int roomId) {
        return game.move(roomId);
    }

    public String shootArrow(int roomId) {
        return game.shoot(roomId);
    }
}