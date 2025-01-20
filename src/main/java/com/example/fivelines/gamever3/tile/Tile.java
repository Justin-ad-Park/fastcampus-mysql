package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.gamelogic.TileControl;

import java.awt.*;

public interface Tile {
    Color getColor();

    boolean isFallable();

    int getX();

    int getY();

    TileType getTile();

    void handleFalling(TileControl gameMainBoard);

    boolean doAction(TileControl gameMainBoard, int dx, int dy);
}
