package com.example.eng2kor;

import java.util.regex.Pattern;

public class Eng2KorConverter {
    public static final int BASE_CODE_KOREAN = 0xAC00;  //0xAC00 = 가
    public static final int NOT_EXISTS_CHARACTER_CODE = -1;
    public static final int CHARACTER_NONE = 0; //종성이 없는 경우 index 증가를 하지 않기 위함
    public static final int CHARACTER_SINGLE = 1;
    public static final int CHARACTER_DOUBLE = 2;
    public static final int CHARACTER_CODE_EMPTY = 0;
    public static final int MAX_LENGTH = 30;
    public static final int MEDIALS_COUNT = 21; //중성(모음) 갯수
    public static final int FINAL_CONSONANTS_COUNT = 28;    //종성(받침) 갯수 : 받침 없음 포함 시 28개


    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";
    // 초성
    static String init = "rRseEfaqQtTdwWczxvg";
    // 중성
    static String[] mid = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l" };
    // 종성
    static String[] fin = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };


    public static Eng2KorResult engToKor(String eng) {
        /** 키워드 사전 검사를 통해 변환 가능한 키워드만 변환을 시도한다. */
        Eng2KorResultType validateResult = validateMessage(eng);

        if(validateResult != Eng2KorResultType.SUCCESS) {
            return new Eng2KorResult(validateResult, eng);
        }

        StringBuffer resultStringBuffer = new StringBuffer();

        CharacterMatchingResult initialCharacter;
        CharacterMatchingResult medialCharacter;
        CharacterMatchingResult finalCharacter;

        for (int i = 0; i < eng.length();) {

            // 숫자특수문자 처리 : 숫자나 특수문자는 한글 변환없이 결과 값에 추가
            if (addIgnoreChars(eng, resultStringBuffer, i)) {
                i++;
                continue;
            }

            try {
                /** 초성코드 추출 */
                initialCharacter = getInitialConsonant(i, eng);
                i = i + initialCharacter.getCharacterLength();    //초성에 해당하는 문자 길이만큼 건너뜀(초성은 무조건 1문자)

                /** 중성코드 추출 */
                medialCharacter = getMedial(i, eng);
                i = i + medialCharacter.getCharacterLength();    //중성에 해당하는 문자 길이 만큼 건너뜀(ㅞ,ㅙ 등 2문자, ㅏㅣㅗㅜ 등 1문자)

                /** 종성코드 추출 */
                finalCharacter = getFinalConsonant(i, eng);
                i = i + finalCharacter.getCharacterLength();

                // 추출한 초성 문자 코드,
                //중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
                resultStringBuffer.append((char) (BASE_CODE_KOREAN
                        + initialCharacter.getCharacterCode()
                        + medialCharacter.getCharacterCode()
                        + finalCharacter.getCharacterCode()));

            } catch (ConversionException ex) {
                return new Eng2KorResult(Eng2KorResultType.FAIL_CONVERT, eng);
            } catch (Exception ex) {
                return new Eng2KorResult(Eng2KorResultType.FAIL_CONVERT, eng);
            }
        }

        return new Eng2KorResult(Eng2KorResultType.SUCCESS, resultStringBuffer.toString());
    }

    /**
     * 길이가 너무 길 경우 변환하지 않음
     * 알파벳 3자 이상이 연속 입력된 경우에만 영어로 인정
     * @param eng
     * @return
     */
    private static Eng2KorResultType validateMessage(String eng) {
        if(eng.length() > MAX_LENGTH)
            return Eng2KorResultType.TOO_LONG;  //변환 성능을 위해 너무 긴 검색 키워드는 변환을 하지 않는다.

        Pattern p = Pattern.compile("[a-zA-Z]{3,}");    //영어가 3글자 이상 있는 경우에만 처리
        if(p.matcher(eng).find() == false) return Eng2KorResultType.NOT_ENOUGH_ENGLISH;

        return Eng2KorResultType.SUCCESS;
    }

    /**
     * 숫자 특수문자 변환 무시
     * @param eng
     * @param sb
     * @param i
     * @return
     */
    private static boolean addIgnoreChars(String eng, StringBuffer sb, int i) {
        if(ignoreChars.indexOf(eng.substring(i, i + CHARACTER_SINGLE)) > NOT_EXISTS_CHARACTER_CODE){
            sb.append(eng.substring(i, i + CHARACTER_SINGLE));
            return true;
        }
        return false;
    }


    /** 초성 추출  */
    static private CharacterMatchingResult getInitialConsonant(int i, String eng) throws ConversionException {
        String c = eng.substring(i, i + CHARACTER_SINGLE);
        int index = init.indexOf(c);

        if (index != NOT_EXISTS_CHARACTER_CODE)
            return new CharacterMatchingResult(index * MEDIALS_COUNT * FINAL_CONSONANTS_COUNT, CHARACTER_SINGLE);

        throw new ConversionException("초성 변환 실패 - 초성이 존재하지 않습니다.");
    }

    /** 중성코드 추출 */
    static private CharacterMatchingResult getMedial(int i, String eng) throws ConversionException {
        int index = getDoubleMedial(i, eng);

        // 복합 중성코드 추출 (ㅞ, ㅙ, ㅝ, ㅘ 등)
        if (index != NOT_EXISTS_CHARACTER_CODE) {
            return new CharacterMatchingResult(index, CHARACTER_DOUBLE);
        }

        //복합 중성 코드가 없으면 단일 중성 코드 추출
        index = getSingleMedial(i, eng);

        if(index != NOT_EXISTS_CHARACTER_CODE)
            return new CharacterMatchingResult(index, CHARACTER_SINGLE);

        throw new ConversionException("중성 변환 실패 - 중성이 존재하지 않습니다.");
    }

    // 두 자로 된 중성을 체크하고, 값을 리턴한다.
    static private int getDoubleMedial(int i, String eng) {
        if ((i + CHARACTER_DOUBLE) > eng.length()) {
            return NOT_EXISTS_CHARACTER_CODE;
        }

        return getMedialCode(eng.substring(i, i + CHARACTER_DOUBLE));

    }

    // 한 자로 된 중성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    static private int getSingleMedial(int i, String eng) {
        if ((i + CHARACTER_SINGLE) > eng.length()) {
            return NOT_EXISTS_CHARACTER_CODE;
        }

        return getMedialCode(eng.substring(i, i + CHARACTER_SINGLE));

    }


    // 중성 코드 변환
    static private int getMedialCode(String c) {
        for (int i = 0; i < mid.length; i++) {
            if (mid[i].equals(c)) {
                return i * FINAL_CONSONANTS_COUNT;  //중성 index * 종성수(28)
            }
        }

        return NOT_EXISTS_CHARACTER_CODE;
    }


    /** 종성 추출  */
    static private CharacterMatchingResult getFinalConsonant(int i, String eng) {
        int finalCode = 0;  //종성(받침) 코드
        int nextMedialCode; //중성(모음) 코드

        /** 복합 종성 코드 추출(ㄱㅅ, ㄴㅁ, ㄹㄱ 등) */
        finalCode = getDoubleFinal(i, eng);

        // 두 자로 이루어진 종성코드 추출
        if (finalCode != NOT_EXISTS_CHARACTER_CODE) {
            // 복합 종성 코드 다음에 중성 코드가 있으면, 복합 종성 코드가 아닌 단일 종성 코드로 처리해야 한다.
            nextMedialCode = getSingleMedial(i + CHARACTER_DOUBLE, eng);    // + CHARACTER_DOUBLE : 복합 종성 다음 케릭터를 판단하기 위함

            //복합 종성 이후에 중성 코드가 따라오는 경우 복합 종성이 아닌 단일 종성, 이후에 초성+중성으로 구성된 것
            if (nextMedialCode == NOT_EXISTS_CHARACTER_CODE) {  //중성 코드가 없으면 복합 종성
                return new CharacterMatchingResult(finalCode, CHARACTER_DOUBLE);
            }

            finalCode = getSingleFinal(i, eng);
            return new CharacterMatchingResult(finalCode, CHARACTER_SINGLE);
        }

        /** 복합 종성이 아닌 경우 추가 로직 처리 */

        //복합 종성이 아닌 경우 다음 문자가 중성이면 현재 문자는 종성이 아닌 초성으로 종성 없음 처리
        nextMedialCode = getSingleMedial(i + CHARACTER_SINGLE, eng);

        if (nextMedialCode != NOT_EXISTS_CHARACTER_CODE) {  //다음 문자가 중성 문자기 때문에 종성 없음으로 처리
            return new CharacterMatchingResult(CHARACTER_CODE_EMPTY, CHARACTER_NONE);   //종성이 아닌 경우 현재 문자를 초성부터 다시 처리하도록 index 증가 안함
        }

        finalCode = getSingleFinal(i, eng);

        // 종성 문자 추출
        if (finalCode != NOT_EXISTS_CHARACTER_CODE)
            return new CharacterMatchingResult(finalCode, CHARACTER_SINGLE);

        return new CharacterMatchingResult(CHARACTER_CODE_EMPTY, CHARACTER_NONE);   //종성이 아닌 경우 현재 문자를 초성부터 다시 처리하도록 index 증가 안함
    }

    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    static private int getSingleFinal(int i, String eng) {
        if ((i + CHARACTER_SINGLE) <= eng.length()) {
            return getFinalCode(eng.substring(i, i + CHARACTER_SINGLE));
        } else {
            return NOT_EXISTS_CHARACTER_CODE;
        }
    }
    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    static private int getDoubleFinal(int i, String eng) {
        if ((i + CHARACTER_DOUBLE) <= eng.length()) {
            return getFinalCode(eng.substring(i, i + CHARACTER_DOUBLE));
        } else {
            return NOT_EXISTS_CHARACTER_CODE;
        }
    }

    // 종성 코드 변환
    static private int getFinalCode(String c) {
        for (int i = 0; i < fin.length; i++) {
            if (fin[i].equals(c)) {
                return i + 1;
            }
        }

        return NOT_EXISTS_CHARACTER_CODE;
    }

}

