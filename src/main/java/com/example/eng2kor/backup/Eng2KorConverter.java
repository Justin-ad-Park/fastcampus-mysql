package com.example.eng2kor.backup;

import com.example.eng2kor.CharacterMatchingResult;
import com.example.eng2kor.Eng2KorResult;
import com.example.eng2kor.Eng2KorResultType;

import java.util.regex.Pattern;

public class Eng2KorConverter {
    public static final int BASE_CODE_KOREAN = 0xAC00;
    public static final int NOT_EXISTS_CHARACTER_CODE = -1;
    public static final int CHARACTER_LENGTH_1 = 1;
    public static final int CHARACTER_LENGTH = 1;

    enum CodeType { chosung, jungsung, jongsung }
    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";
    // 초성
    static String init = "rRseEfaqQtTdwWczxvg";
    // 중성
    static String[] mid = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l" };
    // 종성
    static String[] fin = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };


    /**
     * 알파벳 3자 이상이 연속 입력된 경우에만 영어로 인정
     * @param eng
     * @return
     */
    private static Eng2KorResultType valudateMessage(String eng) {
        if(eng.length() > 30)
            return Eng2KorResultType.TOO_LONG;  //변환 성능을 위해 너무 긴 검색 키워드는 변환을 하지 않는다.

        Pattern p = Pattern.compile("[a-zA-Z]{3,}");    //영어가 3글자 이상 있는 경우에만 처리
        if(p.matcher(eng).find() == false) return Eng2KorResultType.NOT_ENOUGH_ENGLISH;

        return Eng2KorResultType.SUCCESS;
    }

    public static Eng2KorResult engToKor(String eng) {
        /** 키워드 사전 검사를 통해 변환 가능한 키워드만 변환을 시도한다. */
        Eng2KorResultType validateResult = valudateMessage(eng);

        if(validateResult != Eng2KorResultType.SUCCESS) {
            return new Eng2KorResult(validateResult, eng);
        }


        StringBuffer resultStringBuffer = new StringBuffer();
        int initialCode = 0, medialCode = 0, finalCode = 0; //초성, 중성, 종성 위치

        int tempMedialCode, tempFinalCode;
        CharacterMatchingResult initialCharacter;

        for (int i = 0; i < eng.length(); i++) {

            // 숫자특수문자 처리 : 숫자나 특수문자는 한글 변환없이 결과 값에 추가
            if (addIgnoreChars(eng, resultStringBuffer, i)) continue;

            
            /** 초성코드 추출 */
            initialCharacter = getInitialConsonant(eng.substring(i, i + 1));

            if(initialCharacter.getCharacterCode() == NOT_EXISTS_CHARACTER_CODE) {
                return new Eng2KorResult(Eng2KorResultType.FAIL_CONVERT, eng);
            }

            i = i + initialCharacter.getCharacterLength();    //초성에 해당하는 영어1문자 건너뜀


            /** 중성코드 추출 */
            tempMedialCode = getDoubleMedial(i, eng);

            // 두 자로 이루어진 중성코드 추출
            if (tempMedialCode != -1) {
                medialCode = tempMedialCode;
                i += 2; // 두 자로 이루어진 중성이 검출된 경우 영어2문자 건너뜀
            } else {
                // 없다면,
                medialCode = getSingleMedial(i, eng);

                if(medialCode == -1)
                    return new Eng2KorResult(Eng2KorResultType.FAIL_CONVERT, eng);

                i++;    // 한 자로 이루어진 중성코드 추출
            }

            /** 종성코드 추출 */
            tempFinalCode = getDoubleFinal(i, eng);
            // 두 자로 이루어진 종성코드 추출
            if (tempFinalCode != -1) {
                finalCode = tempFinalCode;
                // 그 다음의 중성 문자에 대한 코드를 추출한다.
                tempMedialCode = getSingleMedial(i + 2, eng);
                if (tempMedialCode != -1) {
                    // 코드 값이 있을 경우
                    finalCode = getSingleFinal(i, eng);
                    // 종성 코드 값을 저장한다.
                } else {
                    i++;
                }
            } else {
                // 코드 값이 없을 경우 ,
                tempMedialCode = getSingleMedial(i + 1, eng);
                // 그 다음의 중성 문자에 대한 코드 추출.
                if (tempMedialCode != -1) {
                    // 그 다음에 중성 문자가 존재할 경우,
                    finalCode = 0; // 종성 문자는 없음.
                    i--;
                }
                else {
                    finalCode = getSingleFinal(i, eng);
                    // 종성 문자 추출
                    if (finalCode == -1){
                        finalCode = 0; i--;
                        // 초성,중성 + 숫자,특수문자,
                        //기호가 나오는 경우 index를 줄임.
                    }
                }
            }

            // 추출한 초성 문자 코드,
            //중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
            resultStringBuffer.append((char) (BASE_CODE_KOREAN + initialCode + medialCode + finalCode));
        }

        return new Eng2KorResult(Eng2KorResultType.SUCCESS, resultStringBuffer.toString());
    }

    private static boolean addIgnoreChars(String eng, StringBuffer sb, int i) {
        if(ignoreChars.indexOf(eng.substring(i, i + 1)) > -1){
            sb.append(eng.substring(i, i + 1));
            return true;
        }
        return false;
    }

    // 초성 추출
    static private CharacterMatchingResult getInitialConsonant(String c) {

        int index = init.indexOf(c);

        if (index != NOT_EXISTS_CHARACTER_CODE) {
            return new CharacterMatchingResult(index * 21 * 28, CHARACTER_LENGTH);
        }

        return new CharacterMatchingResult(NOT_EXISTS_CHARACTER_CODE, CHARACTER_LENGTH_1);
    }

    /** * 해당 문자에 따른 코드를 추출한다. * * @param type * 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분 * @param char 해당 문자 */
    static private int getCode(CodeType type, String c) {
        switch (type) {
            case jungsung:
                for (int i = 0; i < mid.length; i++) {
                    if (mid[i].equals(c)) {
                        return i * 28;
                    }
                }
                break;
            case jongsung:
                for (int i = 0; i < fin.length; i++) {
                    if (fin[i].equals(c)) {
                        return i + 1;
                    }
                }
                break;
        }
        return -1;
    }
    // 한 자로 된 중성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    static private int getSingleMedial(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.jungsung, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }
    // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    static private int getDoubleMedial(int i, String eng) {
        int result; if ((i + 2) > eng.length()) {
            return -1;
        } else {
            result = getCode(CodeType.jungsung, eng.substring(i, i + 2));
            if (result != -1) { return result; } else { return -1;
            }
        }
    }
    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    static private int getSingleFinal(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.jongsung, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }
    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    static private int getDoubleFinal(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        } else {
            return getCode(CodeType.jongsung, eng.substring(i, i + 2));
        }
    }

    public static void main(String[] args) {

        System.out.println(engToKor("vnfandjs zhdrnrtn"));




    }

}

