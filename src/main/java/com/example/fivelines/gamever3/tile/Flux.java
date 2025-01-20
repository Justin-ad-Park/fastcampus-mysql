package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.Tile;
import com.example.fivelines.gamever3.TileControl;

import java.awt.*;

public class Flux extends AbstractTileV3 {
    public Flux(int x, int y) {
        super(new Color(204, 255, 204), false,  x, y, Tile.FLUX);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        Air air = new Air(getX(), getY());

        gameMainBoard.setTile(air);

        return true;
    }

}
