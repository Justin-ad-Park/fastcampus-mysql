package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Player extends AbstractTileV3 {
    public Player(int x, int y) {
        super(Color.RED, false, x, y, Tile.PLAYER);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return moving(gameMainBoard, dx, dy);
    }

}
