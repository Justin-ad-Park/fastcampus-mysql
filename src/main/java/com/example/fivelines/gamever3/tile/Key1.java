package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Key1 extends AbstractTileV3 {
    public Key1(int x, int y) {
        super(Color.YELLOW, false, x, y, Tile.KEY1);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return removeKeyAndLock(gameMainBoard, Tile.LOCK1);
    }

}
