package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Box extends AbstractTileV3 {
    public Box(int x, int y ) {
        super(new Color(139, 69, 19), true,  x, y, Tile.BOX);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return moving(gameMainBoard, dx, dy);
    }

}
