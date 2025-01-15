package com.example.fivelines.gamever2;

// GameRenderer.java
import javax.swing.*;
import java.awt.*;

public class GameRenderer extends JPanel {
    private static final int TILE_SIZE = 30;
    private final GameLogic gameLogic;

    public GameRenderer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        setPreferredSize(new Dimension(gameLogic.getMap()[0].length * TILE_SIZE, gameLogic.getMap().length * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tile[][] map = gameLogic.getMap();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                drawTile(g, map[y][x], x, y);
            }
        }

        drawPlayer(g);
    }

    private void drawTile(Graphics g, Tile tile, int x, int y) {
        switch (tile) {
            case FLUX -> g.setColor(new Color(204, 255, 204));
            case UNBREAKABLE -> g.setColor(new Color(153, 153, 153));
            case STONE, FALLING_STONE -> g.setColor(new Color(0, 0, 204));
            case BOX, FALLING_BOX -> g.setColor(new Color(139, 69, 19));
            case KEY1, LOCK1 -> g.setColor(new Color(255, 204, 0));
            case KEY2, LOCK2 -> g.setColor(new Color(0, 204, 255));
            case AIR -> g.setColor(Color.WHITE);
            default -> g.setColor(Color.BLACK);
        }

        if (tile != Tile.AIR && tile != Tile.PLAYER) {
            g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.RED);
        int playerX = gameLogic.getPlayerX();
        int playerY = gameLogic.getPlayerY();
        g.fillRect(playerX * TILE_SIZE, playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}