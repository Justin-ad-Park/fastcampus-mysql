package com.example.eng2kor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Eng2KorConverterTest {
    @Test
    void test() {
        Eng2KorResult convert = Eng2KorConverter.engToKor("vnfandnjs zhdrnrtn");

        Assertions.assertEquals("풀무원 콩국수", convert.getResult());
        Assertions.assertEquals(true, convert.getResultType().isSuccess());
    }

    @Test
    void test1() {
        String keyword = "difqvlaksen";

        Eng2KorResult convert = Eng2KorConverter.engToKor(keyword);
        Assertions.assertEquals("얇피만두", convert.getResult());
        Assertions.assertEquals(true, convert.getResultType().isSuccess());
    }

    @Test
    void test2() {
        String keyword = "dho qnpfr";

        Eng2KorResult convert = Eng2KorConverter.engToKor(keyword);
        Assertions.assertEquals("왜 뷁", convert.getResult());
        Assertions.assertEquals(true, convert.getResultType().isSuccess());
    }


    @Test
    void test3() {
        String keyword = "Rkcl RkrRkrRkR";

        Eng2KorResult convert = Eng2KorConverter.engToKor(keyword);
        Assertions.assertEquals("까치 깍깍깎", convert.getResult());
        Assertions.assertEquals(true, convert.getResultType().isSuccess());
    }


    @Test
    void test4() {
        String keyword = "difq";

        Eng2KorResult convert = Eng2KorConverter.engToKor(keyword);
        Assertions.assertEquals("얇", convert.getResult());
        Assertions.assertEquals(true, convert.getResultType().isSuccess());
    }

    @Test
    void test5() {
        String keyword = "rsef";

        Eng2KorResult convert = Eng2KorConverter.engToKor(keyword);
        Assertions.assertEquals("rsef", convert.getResult());
        Assertions.assertEquals(false, convert.getResultType().isSuccess());
    }

//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        System.out.println("한글을 영타로 입력하세요. : ");
//        String typoText = scan.nextLine();
//
//
//        Eng2KorResult fixedText = Eng2KorConverter.engToKor(typoText);
//        System.out.println(fixedText);
//
//        scan.close();
//    }

}