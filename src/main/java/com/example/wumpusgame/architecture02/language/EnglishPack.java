package com.example.wumpusgame.architecture02.language;

import java.util.EnumMap;
import java.util.Map;

public class EnglishPack implements LanguagePack {
    private final Map<MessageKey, String> texts = new EnumMap<>(MessageKey.class);

    public EnglishPack() {
        texts.put(MessageKey.PROMPT_ACTION, "(M)ove or (S)hoot?");
        texts.put(MessageKey.PROMPT_ROOM, "Which room?");
        texts.put(MessageKey.PROMPT_SHOOT, "Shoot into which room?");
        texts.put(MessageKey.WUMPUS_FOUND, "The Wumpus got you! Game Over.");
        texts.put(MessageKey.WUMPUS_KILLED, "You killed the Wumpus! You win!");
        texts.put(MessageKey.PIT, "You fell into a pit! Game Over.");
        texts.put(MessageKey.BATS, "Bats carried you away!");
        texts.put(MessageKey.MISS, "Missed!");
        texts.put(MessageKey.NO_ARROWS, "No arrows left!");
        texts.put(MessageKey.INVALID_MOVE, "Not a connected room!");
        texts.put(MessageKey.STENCH, "You smell a terrible stench.");
        texts.put(MessageKey.WIND, "You feel a cold wind.");
        texts.put(MessageKey.BAT_NOISE, "You hear flapping.");
        texts.put(MessageKey.SAFE, "You are safe.");
    }

    @Override
    public String get(MessageKey key) {
        return texts.getOrDefault(key, key.name());
    }
}