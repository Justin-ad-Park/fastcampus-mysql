package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Lock1 extends AbstractTile {
    public Lock1(int x, int y) {
        super(Color.YELLOW, false, x, y, TileType.LOCK1);
    }
}
