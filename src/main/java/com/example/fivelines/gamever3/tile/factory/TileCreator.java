package com.example.fivelines.gamever3.tile.factory;

import com.example.fivelines.gamever3.tile.Tile;
import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.tile.implement.*;

public class TileCreator {
    public static Tile createTile(TileType tileType, int x, int y) {

        switch (tileType) {
            case FLUX:
                return new Flux(x, y);
            case UNBREAKER:
                return new Unbreaker(x, y);
            case STONE:
                return new Stone(x, y);
            case BOX:
                return new Box(x, y);
            case PLAYER:
                return new Player(x, y);
            case KEY1:
                return new Key1(x, y);
            case LOCK1:
                return new Lock1(x, y);
            case KEY2:
                return new Key2(x, y);
            case LOCK2:
                return new Lock2(x, y);
            case AIR:
            default:
                return new Air(x, y);
        }
    }
}
