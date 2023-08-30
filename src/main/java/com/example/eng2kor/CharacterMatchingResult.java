package com.example.eng2kor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CharacterMatchingResult {
    private final int characterCode;
    private final int characterLength;
}
