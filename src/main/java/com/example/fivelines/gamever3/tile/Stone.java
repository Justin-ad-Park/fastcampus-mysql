package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;

import java.awt.*;

public class Stone extends AbstractTileV3 {
    public Stone(int x, int y) {
        super(new Color(0, 0, 204), true, x, y, Tile.STONE);
    }

}
