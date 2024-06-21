package com.example.designpattern.builder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class ResourcePoolConfigTest {

    @Test
    void test() {
        ResourcePoolConfig config = new ResourcePoolConfig.Builder()
                .setName("dbconnectonpool")
                .setMaxTotal(16)
                .setMaxIdle(10)
                .setMinIdle(5)
                .build();
    }

    @Test
    void test2_illigalAugumentException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    new ResourcePoolConfig.Builder()
                            .setName("dbconnectonpool")
                            .setMaxTotal(16)
                            .setMaxIdle(5)
                            .setMinIdle(6)
                            .build();
                }
        );

        Assertions.assertEquals("Too Big minIdle", exception.getMessage());

    }

}