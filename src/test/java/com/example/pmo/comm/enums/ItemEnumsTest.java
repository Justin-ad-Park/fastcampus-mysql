package com.example.pmo.comm.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ItemEnumsTest {


    @Test
    void SpecFieldCode_getModifiedDetailMessage() {

        List<String> expirationDate = Arrays.asList("10");
        ItemEnums.SpecFieldCode specFieldCodeEnum = ItemEnums.SpecFieldCode.SPEC_FIELD_01;
        String message = specFieldCodeEnum.getModifiedDetailMessage(expirationDate);

        System.out.println(message);

        Assertions.assertEquals("수령일 기준 10 일 이내 제조(생산) 제품이 배달됩니다.", message);

    }

    @Test
    void 추가메세지_존재케이스() {
        List<String> expirationDate = Arrays.asList("10");
        ItemEnums.SpecFieldCode specFieldCodeEnum = ItemEnums.SpecFieldCode.SPEC_FIELD_01;
        String addtionalMessage = specFieldCodeEnum.getAdditionalMessage("71");

        System.out.println(addtionalMessage);

        Assertions.assertEquals(ItemEnums.SpecFieldCode.SpecFieldAdditional.SPEC_FIELD_01_71.getAdditionalMessage(), addtionalMessage);
    }

    @Test
    void 추가메세지_존재안하는케이스() {
        ItemEnums.SpecFieldCode specFieldCodeEnum = ItemEnums.SpecFieldCode.SPEC_FIELD_02;
        List<String> params = Arrays.asList("홍길동","#풀무원 할인 행사");

        String message = specFieldCodeEnum.getModifiedDetailMessage(params);

        System.out.println(message);

        Assertions.assertEquals("홍길동님, 오늘은 #풀무원 할인 행사(이)가 있습니다.", message);
    }

}