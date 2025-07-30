package com.example.wumpusgame.architecture02.ui;

import com.example.wumpusgame.architecture02.application.GameEngine;
import com.example.wumpusgame.architecture02.application.MoveResult;
import com.example.wumpusgame.architecture02.language.LanguagePack;
import com.example.wumpusgame.architecture02.language.MessageKey;

import java.util.Scanner;

public class ConsoleUI implements GameUI {
    @Override
    public void start(GameEngine engine, LanguagePack language) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("You are in room " + engine.getPlayerRoom());
            System.out.print("Tunnels lead to: ");
            for (int r : engine.getAdjacentRooms()) System.out.print(r + " ");
            System.out.println();

            for (MessageKey clue : engine.getSenses()) {
                System.out.println(language.get(clue));
            }

            System.out.println(language.get(MessageKey.PROMPT_ACTION));
            String action = sc.next().toUpperCase();

            if (action.equals("M")) {
                System.out.println(language.get(MessageKey.PROMPT_ROOM));
                int room = sc.nextInt();
                MoveResult result = engine.movePlayer(room);
                System.out.println(language.get(result.getStatus()));
                if (result.getStatus().equals(MessageKey.WUMPUS_FOUND) || result.getStatus().equals(MessageKey.PIT)) break;
            } else if (action.equals("S")) {
                System.out.println(language.get(MessageKey.PROMPT_SHOOT));
                int room = sc.nextInt();
                MoveResult result = engine.shootArrow(room);
                System.out.println(language.get(result.getStatus()));
                if (result.getStatus().equals(MessageKey.WUMPUS_KILLED)) break;
            }
        }

        sc.close();
    }
}