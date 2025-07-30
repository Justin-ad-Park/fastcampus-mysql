package com.example.wumpusgame.basiccode;

import java.util.*;

class WumpusGame {
    private static final int ROOMS = 20;
    private static final int ARROWS = 5;
    private static final Random random = new Random();

    private int playerRoom;
    private int wumpusRoom;
    private Set<Integer> pits = new HashSet<>();
    private Set<Integer> bats = new HashSet<>();
    private int arrows;

    // 인접 방 구조 (정이십면체: 각 방은 3개 방과 연결)
    private static final int[][] TUNNELS = {
            {1, 4, 7}, {0, 2, 9}, {1, 3, 11}, {2, 4, 13}, {0, 3, 5},
            {4, 6, 14}, {5, 7, 16}, {0, 6, 8}, {7, 9, 17}, {1, 8, 10},
            {9, 11, 18}, {2, 10, 12}, {11, 13, 19}, {3, 12, 14}, {5, 13, 15},
            {14, 16, 19}, {6, 15, 17}, {8, 16, 18}, {10, 17, 19}, {12, 15, 18}
    };

    public WumpusGame() {
        // 시작 설정
        arrows = ARROWS;
        playerRoom = random.nextInt(ROOMS);
        do { wumpusRoom = random.nextInt(ROOMS); } while (wumpusRoom == playerRoom);

        // 구덩이 2개
        while (pits.size() < 2) {
            int r = random.nextInt(ROOMS);
            if (r != playerRoom && r != wumpusRoom) pits.add(r);
        }

        // 박쥐 2마리
        while (bats.size() < 2) {
            int r = random.nextInt(ROOMS);
            if (r != playerRoom && r != wumpusRoom && !pits.contains(r)) bats.add(r);
        }
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hunt the Wumpus ===");

        while (true) {
            describeRoom();

            System.out.print("(M)ove or (S)hoot? ");
            String action = sc.next().toUpperCase();

            if (action.equals("M")) {
                System.out.print("Which room? ");
                int nextRoom = sc.nextInt();
                if (isAdjacent(nextRoom)) {
                    playerRoom = nextRoom;
                    if (checkCurrentRoom()) break;
                } else {
                    System.out.println("Not a connected room!");
                }
            } else if (action.equals("S")) {
                if (arrows == 0) {
                    System.out.println("No arrows left!");
                    continue;
                }
                System.out.print("Shoot into which room? ");
                int target = sc.nextInt();
                arrows--;
                if (isAdjacent(target) && target == wumpusRoom) {
                    System.out.println("You hear a terrible scream. You killed the Wumpus! You win!");
                    break;
                } else {
                    System.out.println("Missed!");
                    // 움퍼스 75% 확률로 이동
                    if (random.nextInt(4) < 3) {
                        int[] moves = TUNNELS[wumpusRoom];
                        wumpusRoom = moves[random.nextInt(3)];
                    }
                }
            } else {
                System.out.println("Invalid action!");
            }
        }
        sc.close();
    }

    private void describeRoom() {
        System.out.println("\nYou are in room " + playerRoom);
        System.out.println("Tunnels lead to " + Arrays.toString(TUNNELS[playerRoom]));

        // 감각 정보 제공
        for (int adj : TUNNELS[playerRoom]) {
            if (adj == wumpusRoom) System.out.println("You smell a terrible stench.");
            if (pits.contains(adj)) System.out.println("You feel a cold wind.");
            if (bats.contains(adj)) System.out.println("You hear flapping nearby.");
        }
    }

    private boolean checkCurrentRoom() {
        if (playerRoom == wumpusRoom) {
            System.out.println("You walked into the Wumpus! Game over.");
            return true;
        }
        if (pits.contains(playerRoom)) {
            System.out.println("You fell into a pit! Game over.");
            return true;
        }
        if (bats.contains(playerRoom)) {
            System.out.println("Super bats grab you and carry you away!");
            playerRoom = random.nextInt(ROOMS);
            checkCurrentRoom(); // 재귀적으로 위험 체크
        }
        return false;
    }

    private boolean isAdjacent(int room) {
        for (int r : TUNNELS[playerRoom]) {
            if (r == room) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new WumpusGame().play();
    }
}