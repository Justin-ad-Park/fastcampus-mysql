package com.example.fivelines.gamever3.gamelogic;

import com.example.fivelines.gamever3.KeyPressCounter;
import com.example.fivelines.gamever3.tile.TileType;
import com.example.fivelines.gamever3.observer.GameStatusObserver;
import com.example.fivelines.gamever3.observer.GameStatusSubject;
import com.example.fivelines.gamever3.tile.implement.Box;
import com.example.fivelines.gamever3.tile.implement.Player;
import com.example.fivelines.gamever3.tile.TileCreator;
import com.example.fivelines.gamever3.tile.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameMainBoardV3 implements PlayerControl, TileControl, GameStatusSubject<Integer> {
    private Tile[][] map;
    private Player player;
    private KeyPressCounter keyPressCounter;
    private final List<GameStatusObserver<Integer>> gameStatusObservers = new ArrayList<>();

    public GameMainBoardV3(TileType[][] initMap, int startX, int startY, KeyPressCounter keyPressCounter) {
        this.initMap(initMap);

        this.player = (Player)map[startX][startY];
        this.keyPressCounter = keyPressCounter;
    }

    //initMap 메소드는 Enum인 Tile[][]을 받아서 TileV3[][]로 변환하는 메소드입니다.
    private void initMap(TileType[][] maps) {
        int height = maps.length;
        int width = maps[0].length;

        map = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = TileCreator.createTile(maps[y][x], x, y);
            }
        }
    }

    @Override
    public void movePlayer(int dx, int dy) {
        player.doAction(this, dx, dy);
        updateFallingTiles();
        keyPressCounter.countKeyPress();
        checkSuccess();
    }


    //맵에 있는 모든 타일를 순회하면서 떨어질 수 있는 타일을 떨어뜨립니다.
    private void updateFallingTiles() {
        Arrays.stream(map).flatMap(Arrays::stream)
                .forEach(tile -> tile.handleFalling(this));

    }

    @Override
    public Tile[][] getTiles() {
        return map;
    }

    @Override
    public Tile getTile(int x, int y) {
        return map[y][x];
    }

    @Override
    public void setTile(Tile tile) {
        map[tile.getY()][tile.getX()] = tile;
    }

    public Tile[][] getMap() {
        return map;
    }

    private void checkSuccess() {
        //가장 우측 하단에 BOX(상자)를 옮기면 성공
        int x = map[0].length - 2;
        int y = map.length - 2;

        if (map[y][x] instanceof Box) {
            notifyObservers(keyPressCounter.getKeyPressCount());
        }
    }

    @Override
    public void registerObserver(GameStatusObserver<Integer> o) {
        gameStatusObservers.add(o);
    }

    @Override
    public void notifyObservers(Integer score) {
        gameStatusObservers.forEach(o -> o.gameSuccess(score));
    }

}
