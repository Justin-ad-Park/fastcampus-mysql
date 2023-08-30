package com.example.eng2kor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Eng2KorResultType {

    SUCCESS(true, "Success")
    , NOT_ENOUGH_ENGLISH(false, "글자수 부족")
    , TOO_LONG(false, "글자수 초과")
    , FAIL_CONVERT(false, "변환 실패");

    private final boolean success;
    private final String resultMessage;

}
