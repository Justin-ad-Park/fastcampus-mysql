package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Stone extends AbstractTile {
    public Stone(int x, int y) {
        super(new Color(0, 0, 204), true, x, y, TileType.STONE);
    }

}
