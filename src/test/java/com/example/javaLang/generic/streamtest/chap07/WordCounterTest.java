package com.example.javaLang.generic.streamtest.chap07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounterTest {

    final String SENTENCE = """
            Nel mezzo del cammin di 
            nostra vita mi ritroval in
            una selva oscurach la dritta
             via era smarrita
            """;

    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);

        return wordCounter.getWordCount();
    }

    @Test
    void wordCounterTest() {
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);

        int wordCount = countWords(stream);

        System.out.println("Found " + wordCount + "words");
        Assertions.assertEquals(18, wordCount);
    }

    @Test
    void wordCounterParallelTest() {
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        int wordCount = countWords(stream);

        System.out.println("Found " + wordCount + "words");
        Assertions.assertEquals(18, wordCount);
    }



}
