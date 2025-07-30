package com.example.wumpusgame.architecture01.domain;

public class Room {
    private final int id;
    private final int[] tunnels;

    public Room(int id, int[] tunnels) {
        this.id = id;
        this.tunnels = tunnels;
    }

    public int getId() { return id; }
    public int[] getTunnels() { return tunnels; }
}