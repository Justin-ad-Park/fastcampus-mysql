package com.example.javaLang.generic.lambdapattern._12responsibilitychain;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LambdaResponsibilityChain {

    UnaryOperator<String> headerProcessing = (String text) -> "[Header]" + text;
    UnaryOperator<String> spellCheckProcessing = (String text) -> text.replaceAll("labda", "Lambda");

    Function<String, String> pipeline = headerProcessing.andThen(spellCheckProcessing);

    @Test
    void 람다로구현한_의무체인() {
        String result = pipeline.apply("Ins't labda really usable?!!!");
        System.out.println(result);
    }
}
