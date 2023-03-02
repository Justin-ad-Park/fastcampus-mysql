package com.example.javaLang.generic.streamtest.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(final String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));    //현재 문자를 소비한다.
        return currentChar < string.length();   //소비할 문자가 남아있으면 true를 리턴한다.
    }

    @Override
    public Spliterator<Character> trySplit() {
        //현재 크기(남은 크기)
        int currentSize = string.length() - currentChar;
        if(currentSize < 10) {  //문자열이 순차 처리가 가능한 정도로 작아졌음을 알리는 null을 반환한다.
            return null;
        }

        int splitPos = currentSize / 2 + currentChar;

        for(; splitPos < string.length(); splitPos++) {
            //현재 위치가 화이트스페이스가 될 때까지 현재 위치를 증가시킨다.
            if(Character.isWhitespace(string.charAt(splitPos)) == false)
                continue;

            Spliterator<Character> spliterator =
                    new WordCounterSpliterator(string.substring(currentChar
                            , splitPos));
            currentChar = splitPos;
            return spliterator;
        }

        return null;    //마지막까지 화이트 스페이스가 나오지 않으면 더 이상 split을 하지 않음
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
