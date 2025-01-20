package com.example.fivelines.gamever3.tile;

import com.example.fivelines.gamever3.gamelogic.TileControl;
import com.example.fivelines.gamever3.tile.implement.Air;

import java.awt.*;
import java.util.Arrays;

public class AbstractTile implements Tile {
    private final Color color;
    private final boolean isFallable;
    private int x;
    private int y;
    private TileType tileType;

    public AbstractTile(Color color, boolean isFallable, int x, int y, TileType tileType) {
        this.color = color;
        this.isFallable = isFallable;
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TileType getTile() {
        return tileType;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isFallable() {
        return isFallable;
    }

    @Override
    public void handleFalling(TileControl gameMainBoard) {
        if(isFallable()) falling(gameMainBoard);
    }

    @Override
    public boolean doAction(TileControl gameMainBoard, int dx, int dy) {
        return false;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    protected void falling(TileControl gameMainBoardV3) {
        if(!isFallable()) return;

        int x = getX();
        int y = getY();

        /**
         * 아래 타일이 Air 타일인 경우 아래 타일과 위치를 변경해서 떨어지는 효과를 준다.
          */
        if (gameMainBoardV3.getTile(x, y + 1) instanceof Air) {
            AbstractTile air = (AbstractTile) gameMainBoardV3.getTile(x, y + 1);
            switchTile(gameMainBoardV3, this, air);
        }
    }

    private static void switchTile(TileControl gameMainBoardV3, AbstractTile tileA, AbstractTile tileB) {
        int x = tileA.getX();
        int y = tileA.getY();

        tileA.setXY(tileB.getX(), tileB.getY());
        tileB.setXY(x, y);

        gameMainBoardV3.setTile(tileA);
        gameMainBoardV3.setTile(tileB);
    }

    // 이동 가능한 타일인 경우
    protected boolean moving(TileControl gameMainBoard, int dx, int dy) {
        int newX = getX() + dx;
        int newY = getY() + dy;

        Tile tile = gameMainBoard.getTile(newX, newY);

        /**
         * 이동하려는 위치(newX, newY)의 타일에 doAction 이벤트를 전달한다.
         * doAction을 전달받은 타일은 필요한 액션 처리 후
         * doAction을 요청한 타일이 자신의 위치로 이동이 가능하면 true를 반환한다.
          */
        if (tile.doAction(gameMainBoard, dx, dy)) {
            Air air = (Air) gameMainBoard.getTile(newX, newY);
            air.setXY(getX(), getY());
            gameMainBoard.setTile(air);

            this.setXY(newX, newY);
            gameMainBoard.setTile(this);

            return true;
        }

        return false;
    }

    protected boolean removeKeyAndLock(TileControl gameMainBoard, TileType tileType) {
        //연결된 Lock1 타일을 Air 타일로 바꾼다.
        Arrays.stream(gameMainBoard.getTiles()).flatMap(Arrays::stream).forEach(tile -> {
            if (tile.getTile() == tileType) {
                Air air = new Air(tile.getX(), tile.getY());
                gameMainBoard.setTile(air);
            }
        });

        // 나 자신(Key1 or Key2)도 Air로 바꾼다.
        Air air = new Air(getX(), getY());
        gameMainBoard.setTile(air);

        return false;
    }


}
