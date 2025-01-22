package com.example.fivelines.gamever2;

import java.util.ArrayList;
import java.util.List;

public class GameLogic implements  PlayerControl, GameStatusSubject<Integer> {
    private Tile[][] map;
    private int playerX;
    private int playerY;
    private KeyPressCounter keyPressCounter;
    private final List<GameStatusObserver<Integer>> gameStatusObservers = new ArrayList<>();

    public GameLogic(Tile[][] initialMap, int startX, int startY, KeyPressCounter keyPressCounter) {
        this.map = initialMap;
        this.playerX = startX;
        this.playerY = startY;
        this.keyPressCounter = keyPressCounter;
    }

    public void updateFallingObjects() {
        for (int y = map.length - 2; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x].handleFalling(this, x, y);
            }
        }
    }

    @Override
    public void movePlayer(int x, int y) {
        move(x, y);

        keyPressCounter.countKeyPress();

        checkSuccess();
    }

    public void move(int dx, int dy) {
        int targetX = playerX + dx;
        int targetY = playerY + dy;


        if (map[targetY][targetX].isWalkable()) {
            movePlayerToTile(targetX, targetY);
        } else if ((map[targetY][targetX] == Tile.BOX || map[targetY][targetX] == Tile.STONE) &&
                map[targetY + dy][targetX + dx].isWalkable() ) {
            moveBox(targetX, targetY, dx, dy);
        } else if (map[targetY][targetX] == Tile.KEY1) {
            removeLocks(Tile.LOCK1);
            movePlayerToTile(targetX, targetY);
        } else if (map[targetY][targetX] == Tile.KEY2) {
            removeLocks(Tile.LOCK2);
            movePlayerToTile(targetX, targetY);
        }
    }

    private void moveBox(int boxX, int boxY, int dx, int dy) {
        map[boxY + dy][boxX + dx] = map[boxY][boxX];
        map[boxY][boxX] = Tile.AIR;
        movePlayerToTile(boxX, boxY);
    }

    private void removeLocks(Tile lockType) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == lockType) {
                    map[y][x] = Tile.AIR;
                }
            }
        }
    }

    private void movePlayerToTile(int x, int y) {
        map[playerY][playerX] = Tile.AIR;
        map[y][x] = Tile.PLAYER;
        playerX = x;
        playerY = y;
    }

    public Tile getTile(int x, int y) {
        return map[y][x];
    }

    public void setTile(int x, int y, Tile tile) {
        map[y][x] = tile;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void checkSuccess() {
        //가장 우측 하단에 BOX(상자)를 옮기면 성공
        int x = map[0].length - 2;
        int y = map.length - 2;

        if (map[y][x] == Tile.BOX ) {
            notifyObservers(keyPressCounter.getKeyPressCount());
        }
    }

    @Override
    public void registerObserver(GameStatusObserver<Integer> o) {
        gameStatusObservers.add(o);
    }

    @Override
    public void notifyObservers(Integer score) {
        gameStatusObservers.forEach(o -> o.gameSuccess(score));
    }

}
