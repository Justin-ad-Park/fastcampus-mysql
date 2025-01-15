package com.example.fivelines.gamever2;

public class GameLogic {
    private Tile[][] map;
    private int playerX;
    private int playerY;

    public GameLogic(Tile[][] initialMap, int startX, int startY) {
        this.map = initialMap;
        this.playerX = startX;
        this.playerY = startY;
    }

    public void updateFallingObjects() {
        for (int y = map.length - 2; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                if ((map[y][x] == Tile.STONE || map[y][x] == Tile.FALLING_STONE || map[y][x] == Tile.BOX || map[y][x] == Tile.FALLING_BOX) && map[y + 1][x] == Tile.AIR) {
                    map[y + 1][x] = (map[y][x] == Tile.BOX) ? Tile.FALLING_BOX : Tile.FALLING_STONE;
                    map[y][x] = Tile.AIR;
                } else if (map[y][x] == Tile.FALLING_STONE) {
                    map[y][x] = Tile.STONE;
                } else if (map[y][x] == Tile.FALLING_BOX) {
                    map[y][x] = Tile.BOX;
                }
            }
        }
    }

    public void move(int targetX, int targetY, int dx, int dy) {
        if (isWalkable(targetY, targetX)) {
            moveToTile(targetX, targetY);
        } else if ((map[targetY][targetX] == Tile.BOX || map[targetY][targetX] == Tile.STONE) &&
                isWalkable(targetY + dy, targetX + dx)) {
            moveBox(targetX, targetY, dx, dy);
        } else if (map[targetY][targetX] == Tile.KEY1) {
            removeLocks(Tile.LOCK1);
            moveToTile(targetX, targetY);
        } else if (map[targetY][targetX] == Tile.KEY2) {
            removeLocks(Tile.LOCK2);
            moveToTile(targetX, targetY);
        }
    }

    private void moveBox(int boxX, int boxY, int dx, int dy) {
        map[boxY + dy][boxX + dx] = map[boxY][boxX];
        map[boxY][boxX] = Tile.AIR;
        moveToTile(boxX, boxY);
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

    private boolean isWalkable(int y, int x) {
        return map[y][x] == Tile.AIR || map[y][x] == Tile.FLUX;
    }

    public void moveToTile(int x, int y) {
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
