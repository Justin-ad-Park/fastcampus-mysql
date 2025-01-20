package com.example.fivelines.gamever3;

import com.example.fivelines.gamever3.tile.TileV3;

public interface TileControl {
    TileV3[][] getTiles();
    TileV3 getTile(int x, int y);
    void setTile(TileV3 tile);
}
