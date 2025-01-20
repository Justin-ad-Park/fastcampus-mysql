package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Air extends AbstractTileV3 {
    public Air(int x, int y) {
        super(Color.WHITE, false, x, y, Tile.AIR);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return true;
    }
}
