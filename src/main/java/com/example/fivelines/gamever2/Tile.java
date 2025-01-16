package com.example.fivelines.gamever2;

import java.awt.*;

public enum Tile {
    AIR(Color.WHITE) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for AIR
        }
    },
    FLUX(new Color(204, 255, 204)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for FLUX
        }
    },
    UNBREAKABLE(new Color(153, 153, 153)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for UNBREAKABLE
        }
    },
    STONE(new Color(0, 0, 204)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_STONE);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    FALLING_STONE(new Color(0, 0, 204)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_STONE);
                gameLogic.setTile(x, y, AIR);
            } else {
                gameLogic.setTile(x, y, STONE);
            }
        }
    },
    BOX(new Color(139, 69, 19)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_BOX);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    FALLING_BOX(new Color(139, 69, 19)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_BOX);
                gameLogic.setTile(x, y, AIR);
            } else {
                gameLogic.setTile(x, y, BOX);
            }
        }
    },
    PLAYER(Color.RED) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for PLAYER
        }
    },
    KEY1(new Color(255, 204, 0)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY1
        }
    },
    LOCK1(new Color(255, 204, 0)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK1
        }
    },
    KEY2(new Color(0, 204, 255)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY2
        }
    },
    LOCK2(new Color(0, 204, 255)) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK2
        }
    };

    private final Color color;

    Tile(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract void handleFalling(GameLogic gameLogic, int x, int y);


}