package com.example.fivelines.gamever3.gamelogic;

import com.example.fivelines.gamever3.tile.Tile;

public interface TileControl {
    Tile[][] getTiles();
    Tile getTile(int x, int y);
    void setTile(Tile tile);
}
