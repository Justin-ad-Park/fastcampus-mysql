package com.example.fivelines.gamever2;

import com.example.fivelines.gamever2.input.*;

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

        GameLogic gameLogic = new GameLogic(initialMap, 1, 1);
        GameRenderer gameRenderer = new GameRenderer(gameLogic);

        Map<Integer, InputAction> keyMappings = new HashMap<>();
        keyMappings.put(KeyEvent.VK_UP, new Up());
        keyMappings.put(KeyEvent.VK_DOWN, new Down());
        keyMappings.put(KeyEvent.VK_LEFT, new Left());
        keyMappings.put(KeyEvent.VK_RIGHT, new Right());

        JFrame frame = new JFrame("Game");
        frame.add(gameRenderer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                InputAction action = keyMappings.get(e.getKeyCode());
                if (action != null) {
                    action.execute(gameLogic);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        Timer timer = new Timer(1000 / 30, e -> {
            gameLogic.updateFallingObjects();
            gameRenderer.repaint();
        });
        timer.start();
    }
}
