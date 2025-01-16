package com.example.fivelines.gamever2;

// GameRenderer.java
import javax.swing.*;
import java.awt.*;

public class GameRenderer extends JPanel implements GameStatusObserver<Integer> {
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
    }

    private void drawTile(Graphics g, Tile tile, int x, int y) {
        g.setColor(tile.getColor());

        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }


    @Override
    public  void gameSuccess(Integer score) {
        JOptionPane.showMessageDialog(this, "Game Success! Score: " + score);
        System.exit(0);
    }

}