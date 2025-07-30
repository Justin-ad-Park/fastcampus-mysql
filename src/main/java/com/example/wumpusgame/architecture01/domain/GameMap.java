package com.example.wumpusgame.architecture01.domain;

public class GameMap {
    private final Room[] rooms;

    public GameMap(int[][] tunnels) {
        rooms = new Room[tunnels.length];
        for (int i = 0; i < tunnels.length; i++) {
            rooms[i] = new Room(i, tunnels[i]);
        }
    }

    public Room getRoom(int id) {
        return rooms[id];
    }

    public int size() {
        return rooms.length;
    }
}
