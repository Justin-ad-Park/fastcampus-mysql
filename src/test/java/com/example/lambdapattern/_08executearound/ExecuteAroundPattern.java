package com.example.lambdapattern._08executearound;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * <pre>
 * "Execute Around" 패턴은 자원 관리, 시간 측정, 트랜잭션 관리와 같은 전/후 처리가 필요한 작업을 위한 디자인 패턴입니다.
 * 이 패턴의 핵심 아이디어는 공통 작업을 라이브러리 코드 내에 캡슐화하고, 사용자가 제공한 코드 블록만을 이 공통 로직의 "around"에서 실행하는 것입니다.
 * 즉, 사용자는 중심이 되는 작업에만 집중할 수 있으며, 전후처리는 패턴이 자동으로 책임집니다.
 *
 * Execute Around 패턴의 장점
 *  -코드 중복 감소: 전후 처리 로직을 한 곳에 모아두기 때문에, 코드 중복이 크게 줄어듭니다.
 *  -가독성 향상: 핵심 비즈니스 로직만 강조되어, 코드의 의도가 더 명확해집니다.
 *  -유지 보수성 향상: 공통 로직이 한 곳에 모여 있어, 수정이 필요할 때 변경 사항을 쉽게 적용할 수 있습니다.
 * </pre>
 */


public class ExecuteAroundPattern {

    // 패턴을 사용하지 않는 경우 아래와 예와 같이 BufferReader를 만드는 코드가 중복된다.
    @Test
    void ReadFileOneLineTest() throws IOException {
        // 리더 생성
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        // 주요 로직
        String contents = br.readLine();

        System.out.println("한줄 : " + contents);
    }

    @Test
    void ReadFileTwoLinesTest() throws IOException {
        // 리더 생성
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        //주요 로직
        String contents = br.readLine();
        contents += br.readLine();

        System.out.println("두줄 : " + contents);
    }

    /**
     * 사용자가 구현해야 하는 핵심 로직만 함수형 인터페이스로 제공
     */
    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }


    /***
     * <pre>
     * 파일을 여는 BufferedReader는 어라운드 패턴으로 처리하고,
     * 사용자의 로직만 메서드로 받아서 호출 처리
     *
     *  - 함수형 인터페이스를 통해 알고리즘을 처리하는 면에서는 전략 패턴과 유사하다.
     *  - 전략 패턴에서 사용자 측에서 구현하는 로직 외의 것들을 대신 처리해주는 부분만 일부 다르다고 볼 수 있다.
     * </pre>
     * @param p
     * @return
     * @throws IOException
     */
    private String processFile(BufferedReaderProcessor p) throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/data.txt")
        );

        return p.process(br);
    }

    /// 패턴을 적용한 결과 - 사용자는 필요한 메서드 구현체(또는 참조)만 제공하면 된다.
    @Test
    void LambdaTest() throws IOException {
        String result = processFile(readOneLine);
        System.out.println("한줄 : " + result);

        result = processFile(readTwoLines);
        System.out.println("두줄 : " + result);
    }


    BufferedReaderProcessor readOneLine = (BufferedReader b) -> b.readLine();
    BufferedReaderProcessor readTwoLines = (BufferedReader b) -> b.readLine() + b.readLine();



}
