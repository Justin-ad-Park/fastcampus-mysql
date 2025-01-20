package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Unbreaker extends AbstractTile {
    public Unbreaker(int x, int y) {
        super(new Color(153, 153, 153), false, x, y, TileType.UNBREAKER);
    }
}
