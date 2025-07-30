package com.example.wumpusgame.architecture02.language;

import java.util.EnumMap;
import java.util.Map;

public class KoreanPack implements LanguagePack {
    private final Map<MessageKey, String> texts = new EnumMap<>(MessageKey.class);

    public KoreanPack() {
        texts.put(MessageKey.PROMPT_ACTION, "(M)이동 또는 (S)발사 ?");
        texts.put(MessageKey.PROMPT_ROOM, "어느 방으로 이동하시겠습니까?");
        texts.put(MessageKey.PROMPT_SHOOT, "어느 방으로 화살을 쏘시겠습니까?");
        texts.put(MessageKey.WUMPUS_FOUND, "움퍼스를 만났습니다! 게임 오버.");
        texts.put(MessageKey.WUMPUS_KILLED, "움퍼스를 처치했습니다! 당신의 승리입니다!");
        texts.put(MessageKey.PIT, "구덩이에 빠졌습니다! 게임 오버.");
        texts.put(MessageKey.BATS, "박쥐가 당신을 다른 방으로 데려갔습니다!");
        texts.put(MessageKey.MISS, "빗나갔습니다!");
        texts.put(MessageKey.NO_ARROWS, "화살이 없습니다!");
        texts.put(MessageKey.INVALID_MOVE, "해당 방으로는 갈 수 없습니다!");
        texts.put(MessageKey.STENCH, "지독한 악취가 납니다.");
        texts.put(MessageKey.WIND, "차가운 바람이 붑니다.");
        texts.put(MessageKey.BAT_NOISE, "날갯짓 소리가 들립니다.");
        texts.put(MessageKey.SAFE, "당신은 안전합니다.");
    }

    @Override
    public String get(MessageKey key) {
        return texts.getOrDefault(key, key.name());
    }
}