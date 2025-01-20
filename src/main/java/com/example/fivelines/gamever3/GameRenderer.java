package com.example.fivelines.gamever3;

// GameRenderer.java

import com.example.fivelines.gamever3.tile.TileV3;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GameRenderer extends JPanel implements GameStatusObserver<Integer> {
    private static final int TILE_SIZE = 30;
    private final GameMainBoardV3 gameMainBoard;

    public GameRenderer(GameMainBoardV3 gameMainBoard) {
        this.gameMainBoard = gameMainBoard;
        setPreferredSize(new Dimension(gameMainBoard.getMap()[0].length * TILE_SIZE, gameMainBoard.getMap().length * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        TileV3[][] map = gameMainBoard.getMap();

        //전체 타일을 그린다.
        Arrays.stream(map).flatMap(Arrays::stream)
                .forEach(tile -> drawTile(g, tile));

    }

    private void drawTile(Graphics g, TileV3 tile) {
        g.setColor(tile.getColor());
        g.fillRect(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }


    @Override
    public  void gameSuccess(Integer score) {
        JOptionPane.showMessageDialog(this, "Game Success! Score: " + score);
        System.exit(0);
    }

}