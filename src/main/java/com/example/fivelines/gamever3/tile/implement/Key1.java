package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Key1 extends AbstractTile {
    public Key1(int x, int y) {
        super(Color.YELLOW, false, x, y, TileType.KEY1);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return removeKeyAndLock(gameMainBoard, TileType.LOCK1);
    }

}
