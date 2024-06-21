package com.example.mocksample.overridemethod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenerateNameServiceTest {

    @Test
    void 테스트() {
        GenerateNameService gn = new GenerateNameService() {

            @Override
            protected String getMnemonic() {
                return "A";
            }
        };

        Assertions.assertEquals("A", gn.getMnemonic());
    }
}
