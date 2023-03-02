package com.example.javaLang.generic.streamtest.chap07;

public class WordCounter {
    private final int wordCount;
    private final boolean isPrevCharacterSpace;

    public WordCounter(int wordCount, boolean isPrevCharacterSpace) {
        this.wordCount = wordCount;
        this.isPrevCharacterSpace = isPrevCharacterSpace;
    }

    public WordCounter accumulate(Character c) {

        //현재 문자가 공백 문자인 경우
        if (Character.isWhitespace(c)) {
            //이전도 공백이었으면 조건 변화 없으니 본 객체 리턴
            if(isPrevCharacterSpace)
                return this;
            else    // 이전이 문자 였으면 단어가 끝난 것으로 조건을 아래와 같이 변경
                return new WordCounter(wordCount, true);
        } else {
            //이전도 문자였다면 조건 변화 없으면 본 객체 리턴
            if(isPrevCharacterSpace == false)
                return this;
            else    //이전 문자가 공백 문자였다면 새로운 단어의 시작이니 단어수 증가, 이전 단어 공백 아닌 것으로 객체 생성 리턴
                return new WordCounter(wordCount + 1, false);
        }
    }

    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(wordCount + wordCounter.wordCount,
                wordCounter.isPrevCharacterSpace);
    }

    public int getWordCount() {
        return wordCount;
    }

}
