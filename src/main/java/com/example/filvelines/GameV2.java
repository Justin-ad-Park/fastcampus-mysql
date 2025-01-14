package com.example.filvelines;

import com.example.filvelines.input.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameV2 extends JPanel implements KeyListener {

    private static final int TILE_SIZE = 30;
    private static final int FPS = 30;
    private static final int SLEEP = 1000 / FPS;

    private enum Tile {
        AIR, FLUX, UNBREAKABLE, PLAYER, STONE, FALLING_STONE, BOX, FALLING_BOX, KEY1, LOCK1, KEY2, LOCK2
    }

    private enum RawInput {
        UP, DOWN, LEFT, RIGHT
    }

    private int playerX = 1;
    private int playerY = 1;
    private Tile[][] map = {
            {Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE},
            {Tile.UNBREAKABLE, Tile.PLAYER, Tile.AIR, Tile.FLUX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
            {Tile.UNBREAKABLE, Tile.STONE, Tile.UNBREAKABLE, Tile.BOX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
            {Tile.UNBREAKABLE, Tile.KEY1, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.UNBREAKABLE, Tile.AIR, Tile.UNBREAKABLE},
            {Tile.UNBREAKABLE, Tile.STONE, Tile.FLUX, Tile.FLUX, Tile.FLUX, Tile.LOCK1, Tile.AIR, Tile.UNBREAKABLE},
            {Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE, Tile.UNBREAKABLE},
    };

    private List<Input> inputs = new ArrayList<>();
    private int keyPressCount = 0;

    public GameV2() {
        setPreferredSize(new Dimension(map[0].length * TILE_SIZE, map.length * TILE_SIZE));
        setFocusable(true);
        addKeyListener(this);

        new Timer(SLEEP, e -> {
            update();
        }).start();
    }

    private void remove(Tile tile) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == tile) {
                    map[y][x] = Tile.AIR;
                }
            }
        }
    }

    private void moveToTile(int newX, int newY) {
        map[playerY][playerX] = Tile.AIR;
        map[newY][newX] = Tile.PLAYER;
        playerX = newX;
        playerY = newY;
    }

    private void moveHorizontal(int dx) {
        int newX = playerX + dx;
        if (map[playerY][newX] == Tile.FLUX || map[playerY][newX] == Tile.AIR) {
            moveToTile(newX, playerY);
        } else if ((map[playerY][newX] == Tile.STONE || map[playerY][newX] == Tile.BOX) &&
                map[playerY][newX + dx] == Tile.AIR && map[playerY + 1][newX] != Tile.AIR) {
            map[playerY][newX + dx] = map[playerY][newX];
            moveToTile(newX, playerY);
        } else if (map[playerY][newX] == Tile.KEY1) {
            remove(Tile.LOCK1);
            moveToTile(newX, playerY);
        } else if (map[playerY][newX] == Tile.KEY2) {
            remove(Tile.LOCK2);
            moveToTile(newX, playerY);
        }
    }

    private void moveVertical(int dy) {
        int newY = playerY + dy;
        if (map[newY][playerX] == Tile.FLUX || map[newY][playerX] == Tile.AIR) {
            moveToTile(playerX, newY);
        } else if (map[newY][playerX] == Tile.KEY1) {
            remove(Tile.LOCK1);
            moveToTile(playerX, newY);
        } else if (map[newY][playerX] == Tile.KEY2) {
            remove(Tile.LOCK2);
            moveToTile(playerX, newY);
        }
    }

    private void checkSuccess() {
        int x = map[0].length - 2;
        int y = map.length - 2;

        if (map[y][x] == Tile.BOX ) {
            JOptionPane.showMessageDialog(this, "Success! Key presses: " + keyPressCount);
            System.exit(0);
        }
    }

    private void update() {
        handleInputs();

        updateMaps();

    }

    private void handleInputs() {
        while (!inputs.isEmpty()) {
            Input input = inputs.remove(0);

            countKeyPress();
            handleInput(input);
            repaint();
            checkSuccess();
        }
    }

    private void countKeyPress() {
        keyPressCount++;
    }

    private void handleInput(Input input) {
        if (input.isLeft())
            moveHorizontal(-1);
        else if (input.isRight())
            moveHorizontal(1);
        else if (input.isUp())
            moveVertical(-1);
        else if (input.isDown())
            moveVertical(1);
    }

    private void updateMaps() {
        for (int y = map.length - 2; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                updateTile(y, x);
            }
        }
    }

    private void updateTile(int y, int x) {
        if ((map[y][x] == Tile.STONE || map[y][x] == Tile.FALLING_STONE) && map[y + 1][x] == Tile.AIR) {
            map[y + 1][x] = Tile.FALLING_STONE;
            map[y][x] = Tile.AIR;
        } else if ((map[y][x] == Tile.BOX || map[y][x] == Tile.FALLING_BOX) && map[y + 1][x] == Tile.AIR) {
            map[y + 1][x] = Tile.FALLING_BOX;
            map[y][x] = Tile.AIR;
        } else if (map[y][x] == Tile.FALLING_STONE) {
            map[y][x] = Tile.STONE;
        } else if (map[y][x] == Tile.FALLING_BOX) {
            map[y][x] = Tile.BOX;
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintMap(g);

        paintPlayer(g);
    }

    private void paintPlayer(Graphics g) {
        //Paint Player
        g.setColor(Color.RED);
        g.fillRect(playerX * TILE_SIZE, playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void paintMap(Graphics g) {
        //Paint Map
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == Tile.FLUX) {
                    g.setColor(new Color(204, 255, 204));
                } else if (map[y][x] == Tile.UNBREAKABLE) {
                    g.setColor(new Color(153, 153, 153));
                } else if (map[y][x] == Tile.STONE || map[y][x] == Tile.FALLING_STONE) {
                    g.setColor(new Color(0, 0, 204));
                } else if (map[y][x] == Tile.BOX || map[y][x] == Tile.FALLING_BOX) {
                    g.setColor(new Color(139, 69, 19));
                } else if (map[y][x] == Tile.KEY1 || map[y][x] == Tile.LOCK1) {
                    g.setColor(new Color(255, 204, 0));
                } else if (map[y][x] == Tile.KEY2 || map[y][x] == Tile.LOCK2) {
                    g.setColor(new Color(0, 204, 255));
                } else {
                    continue;
                }

                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> inputs.add(new Left());
            case KeyEvent.VK_UP, KeyEvent.VK_W -> inputs.add(new Up());
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> inputs.add(new Right());
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> inputs.add(new Down());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameV2");
        GameV2 game = new GameV2();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}