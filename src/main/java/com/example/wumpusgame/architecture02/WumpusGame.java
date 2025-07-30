package com.example.wumpusgame.architecture02;

import com.example.wumpusgame.architecture02.domain.GameMap;
import com.example.wumpusgame.architecture02.application.GameEngine;
import com.example.wumpusgame.architecture02.domain.GameRules;
import com.example.wumpusgame.architecture02.language.EnglishPack;
import com.example.wumpusgame.architecture02.language.KoreanPack;
import com.example.wumpusgame.architecture02.language.LanguagePack;
import com.example.wumpusgame.architecture02.map.DefaultMap;
import com.example.wumpusgame.architecture02.ui.ConsoleUI;
import com.example.wumpusgame.architecture02.ui.GameUI;

public class WumpusGame {
    public static void main(String[] args) {
        // 맵 생성
        GameMap map = DefaultMap.create();

        // 게임 룰 생성
        GameRules rules = new GameRules(map);

        // 엔진 생성
        GameEngine engine = new GameEngine(rules);

        // UI 선택 (전략 패턴)
        GameUI ui = new ConsoleUI();

        // 언어 선택
        LanguagePack language;
        if (args.length > 0 && args[0].equalsIgnoreCase("en")) {
            language = new EnglishPack();
        } else {
            language = new KoreanPack();
        }

        // 게임 시작
        ui.start(engine, language);
    }
}
