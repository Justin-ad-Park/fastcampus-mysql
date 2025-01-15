package com.example.fivelines.gamever2;

public enum Tile {
    AIR {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for AIR
        }
    },
    FLUX {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for FLUX
        }
    },
    UNBREAKABLE {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for UNBREAKABLE
        }
    },
    STONE {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_STONE);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    FALLING_STONE {
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
    BOX {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            if (gameLogic.getTile(x, y + 1) == AIR) {
                gameLogic.setTile(x, y + 1, FALLING_BOX);
                gameLogic.setTile(x, y, AIR);
            }
        }
    },
    FALLING_BOX {
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
    PLAYER {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for PLAYER
        }
    },
    KEY1 {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY1
        }
    },
    LOCK1 {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK1
        }
    },
    KEY2 {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for KEY2
        }
    },
    LOCK2 {
        @Override
        public void handleFalling(GameLogic gameLogic, int x, int y) {
            // No action needed for LOCK2
        }
    };

    public abstract void handleFalling(GameLogic gameLogic, int x, int y);
}