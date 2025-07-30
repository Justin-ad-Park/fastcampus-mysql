package com.example.wumpusgame.architecture02.domain;

import com.example.wumpusgame.architecture02.application.MoveResult;
import com.example.wumpusgame.architecture02.language.MessageKey;

import java.util.*;
public class GameRules {
    private final GameMap map;
    private int playerRoom;
    private int wumpusRoom;
    private Set<Integer> pits = new HashSet<>();
    private Set<Integer> bats = new HashSet<>();
    private int arrows = 5;
    private final Random random = new Random();

    public GameRules(GameMap map) {
        this.map = map;
        init();
    }

    private void init() {
        // 플레이어 시작 위치
        playerRoom = random.nextInt(map.size());

        // 안전한 방 후보 (플레이어 방 제외)
        List<Integer> safeRooms = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            if (i != playerRoom) safeRooms.add(i);
        }

        // Wumpus 배치
        wumpusRoom = pickRandom(safeRooms);

        // 구덩이 2개 배치 (플레이어, Wumpus 제외)
        pits.add(pickRandom(safeRooms));
        pits.add(pickRandom(safeRooms));

        // 박쥐 2마리 배치 (플레이어, Wumpus, 구덩이 제외)
        bats.add(pickRandom(safeRooms));
        bats.add(pickRandom(safeRooms));
    }

    private int pickRandom(List<Integer> list) {
        int idx = random.nextInt(list.size());
        int val = list.get(idx);
        list.remove(idx); // 중복 방지
        return val;
    }

    public MoveResult move(int roomId) {
        if (!isAdjacent(roomId)) return new MoveResult(MessageKey.INVALID_MOVE);
        playerRoom = roomId;
        return checkRoom();
    }

    public MoveResult shoot(int roomId) {
        if (arrows == 0) return new MoveResult(MessageKey.NO_ARROWS);
        arrows--;

        if (isAdjacent(roomId) && roomId == wumpusRoom) return new MoveResult(MessageKey.WUMPUS_KILLED);
        if (random.nextInt(4) < 3) {
            int[] moves = map.getRoom(wumpusRoom).getTunnels();
            wumpusRoom = moves[random.nextInt(moves.length)];

            if (hasEncounteredWumpus()) return new MoveResult(MessageKey.WUMPUS_FOUND);
        }

        return new MoveResult(MessageKey.MISS);
    }

    public List<MessageKey> sense() {
        List<MessageKey> clues = new ArrayList<>();
        for (int adj : map.getRoom(playerRoom).getTunnels()) {
            if (adj == wumpusRoom) clues.add(MessageKey.STENCH);
            if (pits.contains(adj)) clues.add(MessageKey.WIND);
            if (bats.contains(adj)) clues.add(MessageKey.BAT_NOISE);
        }
        return clues;
    }

    private boolean isAdjacent(int roomId) {
        for (int r : map.getRoom(playerRoom).getTunnels()) if (r == roomId) return true;
        return false;
    }

    private MoveResult checkRoom() {
        if (hasEncounteredWumpus()) return new MoveResult(MessageKey.WUMPUS_FOUND);
        if (pits.contains(playerRoom)) return new MoveResult(MessageKey.PIT);
        if (bats.contains(playerRoom)) {
            playerRoom = random.nextInt(map.size());
            return new MoveResult(MessageKey.BATS);
        }
        return new MoveResult(MessageKey.SAFE);
    }

    private boolean hasEncounteredWumpus() {
        return playerRoom == wumpusRoom;
    }

    public int getPlayerRoom() { return playerRoom; }
    public int[] getAdjacentRooms() { return map.getRoom(playerRoom).getTunnels(); }
}