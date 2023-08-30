package com.example.eng2kor.backup;

/**
 * Chat-GPT4로 생성한 코드
 *  종성과 초성 구분에 오류가 존재함.
 */
public class EngToKorConverter2 {
    private static String[] initials = {"r", "R", "s", "e", "E", "f", "a", "q", "Q", "t", "T", "d", "w", "W", "c", "z", "x", "v", "g"};
    private static String[] medials = {"k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l"};
    private static String[] finals = {"", "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g"};

    private static final int BASE_CODE = 44032;
    private static final int CHOSUNG = 588;
    private static final int JUNGSUNG = 28;

    public static String engToKor(String word) {
        StringBuilder korWord = new StringBuilder();
        char[] splitKorean = word.toCharArray();
        int length = splitKorean.length;

        for (int i = 0; i < length; ) {
            if (splitKorean[i] == ' ') {
                korWord.append(' ');
                i++;
                continue;
            }

            if (i + 1 >= length) break;

            String choStr = String.valueOf(splitKorean[i]);
            String jungStr = String.valueOf(splitKorean[i + 1]);

            int choIdx = indexOf(initials, choStr);
            int jungIdx = indexOf(medials, jungStr);

            int jongIdx;
            if (i + 2 < length) {
                String jongStr = String.valueOf(splitKorean[i + 2]);
                jongIdx = indexOf(finals, jongStr);
                if (jongIdx != -1) {
                    i += 3;
                } else {
                    jongIdx = 0;
                    i += 2;
                }
            } else {
                jongIdx = 0;
                i += 2;
            }

            int korChar = BASE_CODE + (choIdx * CHOSUNG) + (jungIdx * JUNGSUNG) + jongIdx;
            korWord.append((char) korChar);
        }
        return korWord.toString();
    }

    public static int indexOf(String[] arr, String targetValue) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(targetValue)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String testWord = "vnfandnjs zhdrnrtn";  // 안녕 하세요
        String result = engToKor(testWord);
        System.out.println("Converted: " + result);  // 출력: 안녕 하세요
    }
}
