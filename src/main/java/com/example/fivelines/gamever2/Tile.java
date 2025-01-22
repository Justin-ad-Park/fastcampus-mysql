package com.example.fivelines.gamever2;

import java.awt.*;

public enum Tile {
    AIR(Color.WHITE, true, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for AIR
        }
    },
    FLUX(new Color(204, 255, 204), true, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for FLUX
        }
    },
    UNBREAKABLE(new Color(153, 153, 153), false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for UNBREAKABLE
        }
    },
    STONE(new Color(0, 0, 204), false, true) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, STONE);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    BOX(new Color(139, 69, 19), false, true) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, BOX);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    PLAYER(Color.RED, false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for PLAYER
        }
    },
    KEY1(new Color(255, 204, 0), false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY1
        }
    },
    LOCK1(new Color(255, 204, 0), false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK1
        }
    },
    KEY2(new Color(0, 204, 255), false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY2
        }
    },
    LOCK2(new Color(0, 204, 255), false, false) {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK2
        }
    };

    private final Color color;
    private final boolean isWalkable;
    private final boolean isFallable;


    Tile(Color color, boolean isWalkable, boolean isFallable) {
        this.color = color;
        this.isWalkable = isWalkable;
        this.isFallable = isFallable;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isFallable() {
        return isFallable;
    }

    public abstract void handleFalling(GameLogic gameLogic, int x, int y);


}