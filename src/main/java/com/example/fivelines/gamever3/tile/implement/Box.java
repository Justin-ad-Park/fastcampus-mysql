package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Box extends AbstractTile {
    public Box(int x, int y ) {
        super(new Color(139, 69, 19), true,  x, y, TileType.BOX);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return moving(gameMainBoard, dx, dy);
    }

}
