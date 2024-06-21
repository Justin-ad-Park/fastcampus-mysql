package com.example.mocksample.overridemethod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GenerateNameService {

    static final List<String> mnemonic = Arrays.asList(
            "alpha", "bravo", "charlie",
            "delta", "echo", "foxtrot",
            "golf", "hotel", "india",
            "juliet", "kilo", "lima",
            "mike", "november", "oscar",
            "papa", "quebec", "romeo",
            "sierra", "tango", "uniform",
            "victor", "whiskey", "xray",
            "yankee", "zulu"
    );

    static final int MaxBound = 26;

    static final Random rand = new Random();


    @Test
    void Test() {
        int testSize = 4;

        List<String> mnemonic = generateMnemonics(testSize);

        mnemonic.stream().forEach(System.out::println);

        Assertions.assertTrue(mnemonic.size() == testSize);

    }

    @Test
    void getMnemonicTest() {
        String result = getMnemonic();

        Assertions.assertTrue(result != "");
    }

    protected List<String> generateMnemonics(int size) {

        return Stream.generate(this::getMnemonic)
                .limit(size)
                .toList();
    }

    protected String getMnemonic() {
        return mnemonic.get(rand.nextInt(MaxBound));
    }

}
