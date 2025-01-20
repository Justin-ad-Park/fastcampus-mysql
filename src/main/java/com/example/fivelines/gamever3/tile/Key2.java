package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Key2 extends AbstractTileV3 {
    public Key2(int x, int y) {
        super(Color.GREEN, false, x, y, Tile.KEY2);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return removeKeyAndLock(gameMainBoard, Tile.LOCK1);
    }
}
