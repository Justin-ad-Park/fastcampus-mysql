package com.example.fivelines.gamever2;

import com.example.fivelines.gamever2.input.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class GameStarter {
    public static void main(String[] args) {
        Tile[][] initialMap = {
                {Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE},
                {Tile.UNBREAKABLE, Tile.PLAYER, Tile.AIR, Tile.FLUX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
                {Tile.UNBREAKABLE, Tile.STONE, Tile.UNBREAKABLE, Tile.BOX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
                {Tile.UNBREAKABLE, Tile.KEY1, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
                {Tile.UNBREAKABLE, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.FLUX, Tile.LOCK1, Tile.AIR, Tile.UNBREAKABLE},
                {Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE}
        };

        KeyPressCounter keyPressCounter = new KeyPressCounter();

        GameLogic gameLogic = new GameLogic(initialMap, 1, 1, keyPressCounter);
        GameRenderer gameRenderer = new GameRenderer(gameLogic);

        //게임 성공 상태를 처리하기 위해 옵저버 패턴을 이용
        gameLogic.registerObserver(gameRenderer);

        JFrame frame = initFrame(gameRenderer);

        initKeyListener(gameLogic, frame);

        Timer timer = new Timer(1000 / 30, e -> {
            gameLogic.updateFallingObjects();
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

    private static void initKeyListener(GameLogic gameLogic, JFrame frame) {

        Map<Integer, InputAction> keyMappings = initKeyMappings();

        frame.addKeyListener(createKeyListener(gameLogic, keyMappings));
    }

    private static @NotNull KeyListener createKeyListener(GameLogic gameLogic, Map<Integer, InputAction> keyMappings) {
        return new KeyListener() {

            NoneAction noneAction = new NoneAction();

            @Override
            public void keyPressed(KeyEvent e) {
                InputAction action = keyMappings.getOrDefault(e.getKeyCode(), noneAction);
                action.execute(gameLogic);
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
