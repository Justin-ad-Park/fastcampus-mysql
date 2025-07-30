package com.example.wumpusgame.architecture02.ui;

import com.example.wumpusgame.architecture02.application.GameEngine;
import com.example.wumpusgame.architecture02.language.LanguagePack;

public interface GameUI {
    void start(GameEngine engine, LanguagePack language);
}