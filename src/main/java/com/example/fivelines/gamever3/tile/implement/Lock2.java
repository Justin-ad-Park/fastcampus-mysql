package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Lock2 extends AbstractTile {
    public Lock2(int x, int y) {
        super(Color.GREEN, false, x, y, TileType.LOCK2);
    }
}
