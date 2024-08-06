package com.example.javaLang.generic.streamtest.parallel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounter {
    static Pattern punctuation = Pattern.compile("[\\p{Punct}“”‘’™•]"); // 구두점, 특수문자
    static Pattern whitespace = Pattern.compile("[\\p{Space}—]");    // 공백 문자, 대시 문자(—)
    static Pattern words = Pattern.compile("[\\p{L}\\p{N}]+");       // 유니코드 문자(알파벳 포함), 숫자, 밑줄이 하나 이상 연속된 문자열을 매칭

    static Map<String, Integer> countWords(String path) {
        var filePath = Paths.get(path);
        Map<String, Integer> wordCount = null;

        try {
            var fileContent = Files.readString(filePath);

            wordCount =
                    Stream.of(fileContent) // 파일 내용을 스트림으로 변환 (여기서는 단일 요소 스트림)
                            .map(punctuation::matcher) // 각 요소(파일 내용)에 대해 구두점 문자를 찾는 Matcher 생성
                            .map(matcher -> matcher.replaceAll("")) // 구두점 문자를 빈 문자열로 대체 (제거)
                            .map(whitespace::split) // 공백 문자를 기준으로 문자열을 분할하여 단어 배열로 변환
                            .flatMap(Arrays::stream) // 단어 배열을 스트림으로 변환 (각 단어를 개별 요소로)
                            .filter(word -> !word.isBlank())
                            .filter(word -> words.matcher(word).matches()) // 단어 형식을 만족하는 문자열만 필터링
                            .map(String::toLowerCase) // 모든 단어를 소문자로 변환 (대소문자 구분 없이 세기 위해)
                            .collect(Collectors.toMap(
                                    Function.identity(), // 각 단어를 키로 사용
                                    w -> 1,              // 각 단어의 초기 값은 1
                                    Integer::sum));      // 동일한 키가 있을 경우 값을 더함
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wordCount;
    }

    static Map<String, Integer> countWordsParallel(String path) {
        var filePath = Paths.get(path);
        Map<String, Integer> wordCount = null;

        try(Stream<String> stream = Files.lines(filePath)) {
            wordCount = stream
                    .parallel()
                    .map(punctuation::matcher) // 각 요소(파일 내용)에 대해 구두점 문자를 찾는 Matcher 생성
                    .map(matcher -> matcher.replaceAll("")) // 구두점 문자를 빈 문자열로 대체 (제거)
                    .map(whitespace::split) // 공백 문자를 기준으로 문자열을 분할하여 단어 배열로 변환
                    .flatMap(Arrays::stream) // 단어 배열을 스트림으로 변환 (각 단어를 개별 요소로)
                    .filter(word -> !word.isBlank())
                    .filter(word -> words.matcher(word).matches()) // 단어 형식을 만족하는 문자열만 필터링
                    .map(String::toLowerCase) // 모든 단어를 소문자로 변환 (대소문자 구분 없이 세기 위해)
                    .collect(Collectors.toMap(
                        Function.identity(), // 각 단어를 키로 사용
                        w -> 1,              // 각 단어의 초기 값은 1
                        Integer::sum));      // 동일한 키가 있을 경우 값을 더함

            return wordCount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() {
        String path = "src/main/resources/war-and-peace.txt";
        Map<String, Integer> result = countWords(path);

        result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10).forEach(System.out::println);

    }

    @Test
    void test2() {
        String path = "src/main/resources/song.txt";
        Map<String, Integer> result = countWords(path);

        result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);

    }

    @Test
    void test3() {
        String path = "src/main/resources/war-and-peace.txt";
        Map<String, Integer> result = countWordsParallel(path);

        result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10).forEach(System.out::println);

    }

    @Test
    void test4() {
        String path = "src/main/resources/song.txt";
        Map<String, Integer> result = countWordsParallel(path);

        result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);

    }

    /**
     * 유니코드 프로퍼티	설명	예시 문자
     * \p{Punct}	구두점 문자	! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` {
     * \p{Alpha}	알파벳 문자 (영문자 및 유니코드 문자 포함)	A, B, C, ... z, α, β, ...
     * \p{Digit}	숫자 (0-9)	0, 1, 2, 3, 4, 5, 6, 7, 8, 9
     * \p{Alnum}	알파벳 문자 및 숫자	a, b, c, ... 1, 2, 3, ...
     * \p{Blank}	공백 문자 및 탭	(space), \t
     * \p{Cntrl}	제어 문자 (ASCII 컨트롤 문자)	\u0000-\u001F, \u007F
     * \p{Graph}	출력 가능한 모든 문자 (공백 제외)	a, A, 1, 2, !, @, ...
     * \p{Lower}	소문자	a, b, c, ... z
     * \p{Upper}	대문자	A, B, C, ... Z
     * \p{Space}	공백 문자 (공백, 탭, 개행 등 포함)	(space), \t, \n, \r, \f, \v
     * \p{XDigit}	16진수 숫자	0-9, A-F, a-f
     * \p{Print}	출력 가능한 모든 문자 (공백 포함)	a, A, 1, 2, !, @, (space) ...
     *
     * \p{L}: "Letter" 범주에 속하는 모든 문자. 모든 알파벳 문자 + 라틴어 등 모든 문자 포함합니다.
     * \p{N}: "Number" 범주에 속하는 모든 숫자.
     * \p{Z}: 공백 문자.
     * \p{P}: 구두점 문자.
     * \p{Lu}: 대문자 문자.
     * \p{Ll}: 소문자 문자.
     */
}
