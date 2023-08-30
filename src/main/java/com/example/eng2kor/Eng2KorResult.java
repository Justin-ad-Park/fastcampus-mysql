package com.example.eng2kor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Eng2KorResult {
    public Eng2KorResult(final Eng2KorResultType resultType, final String result) {
        this.resultType = resultType;
        this.result = result;
    }

    private Eng2KorResultType resultType;
    private String result;

    @Override
    public String toString() {
        return result;
    }

}
