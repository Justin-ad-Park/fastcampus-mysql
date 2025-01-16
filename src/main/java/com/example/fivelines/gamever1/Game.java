package com.example.filvelines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements KeyListener {

    private static final int TILE_SIZE = 30;
    private static final int FPS = 30;
    private static final int SLEEP = 1000 / FPS;

    private enum Tile {
        AIR, FLUX, UNBREAKABLE, PLAYER, STONE, FALLING_STONE, BOX, FALLING_BOX, KEY1, LOCK1, KEY2, LOCK2
    }

    private enum Input {
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

    public Game() {
        setPreferredSize(new Dimension(map[0].length * TILE_SIZE, map.length * TILE_SIZE));
        setFocusable(true);
        addKeyListener(this);

        new Timer(SLEEP, e -> {
            update();
            repaint();
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

    private void update() {
        while (!inputs.isEmpty()) {
            Input input = inputs.remove(0);
            switch (input) {
                case LEFT -> moveHorizontal(-1);
                case RIGHT -> moveHorizontal(1);
                case UP -> moveVertical(-1);
                case DOWN -> moveVertical(1);
            }
        }

        for (int y = map.length - 2; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
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
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
                }

                if (map[y][x] != Tile.AIR && map[y][x] != Tile.PLAYER) {
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        g.setColor(Color.RED);
        g.fillRect(playerX * TILE_SIZE, playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> inputs.add(Input.LEFT);
            case KeyEvent.VK_UP, KeyEvent.VK_W -> inputs.add(Input.UP);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> inputs.add(Input.RIGHT);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> inputs.add(Input.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
