package com.example.wumpusgame.architecture01.ui;

import com.example.wumpusgame.architecture01.application.GameService;
import com.example.wumpusgame.architecture01.domain.GameMap;
import com.example.wumpusgame.architecture01.domain.WumpusGame;

import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args) {
        int[][] tunnels = {
                {1,4,7}, {0,2,9}, {1,3,11}, {2,4,13}, {0,3,5},
                {4,6,14}, {5,7,16}, {0,6,8}, {7,9,17}, {1,8,10},
                {9,11,18}, {2,10,12}, {11,13,19}, {3,12,14}, {5,13,15},
                {14,16,19}, {6,15,17}, {8,16,18}, {10,17,19}, {12,15,18}
        };

        GameMap map = new GameMap(tunnels);
        WumpusGame game = new WumpusGame(map);
        GameService service = new GameService(game);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println(service.describe());
            System.out.print("(M)ove or (S)hoot? ");
            String action = sc.next().toUpperCase();

            if (action.equals("M")) {
                System.out.print("Which room? ");
                int room = sc.nextInt();
                String result = service.movePlayer(room);
                if (result.equals("WUMPUS_FOUND")) { System.out.println("The Wumpus got you! Game Over."); break; }
                if (result.equals("PIT")) { System.out.println("You fell into a pit! Game Over."); break; }
                if (result.equals("BATS")) { System.out.println("Bats carried you away!"); }
            } else if (action.equals("S")) {
                System.out.print("Shoot into which room? ");
                int room = sc.nextInt();
                String result = service.shootArrow(room);
                if (result.equals("WUMPUS_KILLED")) { System.out.println("You killed the Wumpus! You win!"); break; }
                System.out.println(result);
            }
        }
        sc.close();
    }
}