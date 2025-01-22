package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.AbstractTile;

import java.awt.*;

public class Player extends AbstractTile {
    public Player(int x, int y) {
        super(Color.RED, false, x, y, TileType.PLAYER);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return moving(gameMainBoard, dx, dy);
    }

}
