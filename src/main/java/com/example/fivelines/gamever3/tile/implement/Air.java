package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Air extends AbstractTile {
    public Air(int x, int y) {
        super(Color.WHITE, false, x, y, TileType.AIR);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return true;
    }
}
