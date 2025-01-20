package com.example.fivelines.gamever3;

import com.example.fivelines.gamever3.input.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class GameStarter {
    public static void main(String[] args) {
        Tile[][] initialMap = {
                {Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER},
                {Tile.UNBREAKER, Tile.PLAYER, Tile.AIR, Tile.FLUX, Tile.FLUX, Tile.UNBREAKER, Tile.AIR, Tile.UNBREAKER},
                {Tile.UNBREAKER, Tile.STONE, Tile.UNBREAKER, Tile.BOX, Tile.FLUX, Tile.UNBREAKER, Tile.AIR, Tile.UNBREAKER},
                {Tile.UNBREAKER, Tile.KEY1, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.UNBREAKER, Tile.AIR, Tile.UNBREAKER},
                {Tile.UNBREAKER, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.FLUX, Tile.LOCK1, Tile.AIR, Tile.UNBREAKER},
                {Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER, Tile.UNBREAKER}
        };

        KeyPressCounter keyPressCounter = new KeyPressCounter();

        GameMainBoardV3 gameMainBoard = new GameMainBoardV3(initialMap, 1, 1, keyPressCounter);
        GameRenderer gameRenderer = new GameRenderer(gameMainBoard);

        //게임 성공 상태를 처리하기 위해 옵저버 패턴을 이용
        gameMainBoard.registerObserver(gameRenderer);

        JFrame frame = initFrame(gameRenderer);

        initKeyListener(gameMainBoard, frame);

        Timer timer = new Timer(1000 / 30, e -> {
            gameRenderer.repaint();
        });
        timer.start();
    }

    private static @NotNull JFrame initFrame(GameRenderer gameRenderer) {
        JFrame frame = new JFrame("Game");
        frame.add(gameRenderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    private static void initKeyListener(GameMainBoardV3 gameMainBoard, JFrame frame) {

        Map<Integer, InputAction> keyMappings = initKeyMappings();

        frame.addKeyListener(createKeyListener(gameMainBoard, keyMappings));
    }

    private static @NotNull KeyListener createKeyListener(GameMainBoardV3 gameMainBoard, Map<Integer, InputAction> keyMappings) {
        return new KeyListener() {

            NoneAction noneAction = new NoneAction();

            @Override
            public void keyPressed(KeyEvent e) {
                InputAction action = keyMappings.getOrDefault(e.getKeyCode(), noneAction);
                action.execute(gameMainBoard);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        };
    }

    private static @NotNull Map<Integer, InputAction> initKeyMappings() {
        Map<Integer, InputAction> keyMappings = new HashMap<>();
        keyMappings.put(KeyEvent.VK_UP, new Up());
        keyMappings.put(KeyEvent.VK_DOWN, new Down());
        keyMappings.put(KeyEvent.VK_LEFT, new Left());
        keyMappings.put(KeyEvent.VK_RIGHT, new Right());
        return keyMappings;
    }


}
