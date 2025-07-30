package com.example.wumpusgame.architecture01.domain;

import java.util.*;

public class WumpusGame {
    private final GameMap map;
    private int playerRoom;
    private int wumpusRoom;
    private Set<Integer> pits = new HashSet<>();
    private Set<Integer> bats = new HashSet<>();
    private int arrows = 5;
    private final Random random = new Random();

    public WumpusGame(GameMap map) {
        this.map = map;
        init();
    }

    private void init() {
        initPlayer();

        initWumpus();

        initMultiObject(pits);

        initMultiObject(bats);
    }

    private void initMultiObject(Set<Integer> objects) {
        while (objects.size() < 2) objects.add(random.nextInt(map.size()));
    }

    private void initWumpus() {
        do { wumpusRoom = random.nextInt(map.size()); } while (wumpusRoom == playerRoom);
    }

    private void initPlayer() {
        playerRoom = random.nextInt(map.size());
    }

    public String move(int roomId) {
        if (!isAdjacent(roomId)) return "Not connected!";
        playerRoom = roomId;
        return checkRoom();
    }

    public String shoot(int roomId) {
        if (arrows == 0) return "No arrows left!";
        arrows--;
        if (isAdjacent(roomId) && roomId == wumpusRoom) return "WUMPUS_KILLED";
        // Wumpus 이동
        if (random.nextInt(4) < 3) {
            int[] tunnels = map.getRoom(wumpusRoom).getTunnels();
            wumpusRoom = tunnels[random.nextInt(tunnels.length)];
        }
        return "Missed!";
    }

    public List<String> sense() {
        List<String> clues = new ArrayList<>();
        for (int adj : map.getRoom(playerRoom).getTunnels()) {
            if (adj == wumpusRoom) clues.add("You smell a terrible stench.");
            if (pits.contains(adj)) clues.add("You feel a cold wind.");
            if (bats.contains(adj)) clues.add("You hear flapping.");
        }
        return clues;
    }

    private boolean isAdjacent(int roomId) {
        for (int r : map.getRoom(playerRoom).getTunnels()) if (r == roomId) return true;
        return false;
    }

    private String checkRoom() {
        if (playerRoom == wumpusRoom) return "WUMPUS_FOUND";
        if (pits.contains(playerRoom)) return "PIT";
        if (bats.contains(playerRoom)) {
            initPlayer();
            return "BATS";
        }
        return "SAFE";
    }

    public int getPlayerRoom() { return playerRoom; }
    public int[] getAdjacentRooms() { return map.getRoom(playerRoom).getTunnels(); }
}
