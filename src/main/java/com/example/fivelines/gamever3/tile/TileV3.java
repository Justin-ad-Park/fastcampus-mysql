package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public interface TileV3 {
    Color getColor();

    boolean isFallable();

    int getX();

    int getY();

    Tile getTile();

    void handleFalling(TileControl gameMainBoard);

    boolean doAction(TileControl gameMainBoard, int dx, int dy);
}
