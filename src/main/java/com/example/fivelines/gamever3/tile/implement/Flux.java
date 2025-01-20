package com.example.fivelines.gamever3.tile.implement;

import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.AbstractTile;
import com.example.fivelines.gamever3.tile.Tile;

import java.awt.*;

public class Flux extends AbstractTile {
    public Flux(int x, int y) {
        super(new Color(204, 255, 204), false,  x, y, TileType.FLUX);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        int callerX = getX() + (dx * -1);
        int callerY = getY() + (dy * -1);

        // doAction을 호출한 Tile을 구한다.
        Tile tile = gameMainBoard.getTile(callerX, callerY);

        // doAction을 호출한 Tile이 Player인 경우에만 Air로 변경한다.
        if(tile instanceof Player ) {
            Air air = new Air(getX(), getY());
            gameMainBoard.setTile(air);
            return true;
        }

        return false;
    }

}
