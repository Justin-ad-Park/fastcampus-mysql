package com.example.javaLang.generic.streamtest.collect.testresult;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTest {
    List<TestResult> testResults = Arrays.asList(

            new TestResult("Alice", "1학년 2학기", "국어", 92),
            new TestResult("Alice", "1학년 2학기", "영어", 90),
            new TestResult("Alice", "1학년 2학기", "수학", 95),
            new TestResult("Alice", "1학년 2학기", "과학", 93),
            // Repeat for each exam period for Alice
            // Repeat for other students, adjusting scores according to the criteria
            new TestResult("Bob", "1학년 2학기", "국어", 82),
            new TestResult("Bob", "1학년 2학기", "영어", 86),
            new TestResult("Bob", "1학년 2학기", "수학", 81),
            new TestResult("Bob", "1학년 2학기", "과학", 74),
            // Continue for each student and exam period
            // Example for a student with scores below 70
            new TestResult("Charlie", "1학년 2학기", "국어", 75),
            new TestResult("Charlie", "1학년 2학기", "영어", 76),
            new TestResult("Charlie", "1학년 2학기", "수학", 56),
            new TestResult("Charlie", "1학년 2학기", "과학", 71),

            new TestResult("Student1", "1학년 1학기", "국어", 85),
            new TestResult("Student1", "1학년 1학기", "영어", 82),
            new TestResult("Student1", "1학년 1학기", "수학", 90),
            new TestResult("Student1", "1학년 1학기", "과학", 88),
            new TestResult("Student1", "1학년 2학기", "국어", 87),
            new TestResult("Student1", "1학년 2학기", "영어", 85),
            new TestResult("Student1", "1학년 2학기", "수학", 92),
            new TestResult("Student1", "1학년 2학기", "과학", 89),

            new TestResult("Alice", "1학년 1학기", "국어", 92),
            new TestResult("Alice", "1학년 1학기", "영어", 100),
            new TestResult("Alice", "1학년 1학기", "수학", 98),
            new TestResult("Alice", "1학년 1학기", "과학", 93),
            // Repeat for each exam period for Alice
            // Repeat for other students, adjusting scores according to the criteria
            new TestResult("Bob", "1학년 1학기", "국어", 85),
            new TestResult("Bob", "1학년 1학기", "영어", 82),
            new TestResult("Bob", "1학년 1학기", "수학", 87),
            new TestResult("Bob", "1학년 1학기", "과학", 84),
            // Continue for each student and exam period
            // Example for a student with scores below 70
            new TestResult("Charlie", "1학년 1학기", "국어", 65),
            new TestResult("Charlie", "1학년 1학기", "영어", 67),
            new TestResult("Charlie", "1학년 1학기", "수학", 66),
            new TestResult("Charlie", "1학년 1학기", "과학", 68),

            // Repeat for each student
            new TestResult("Student2", "1학년 1학기", "국어", 78),
            new TestResult("Student2", "1학년 1학기", "영어", 80),
            new TestResult("Student2", "1학년 1학기", "수학", 75),
            new TestResult("Student2", "1학년 1학기", "과학", 82),
            new TestResult("Student2", "1학년 2학기", "국어", 80),
            new TestResult("Student2", "1학년 2학기", "영어", 83),
            new TestResult("Student2", "1학년 2학기", "수학", 77),
            new TestResult("Student2", "1학년 2학기", "과학", 85)
            // Continue adding TestResult instances for all students and subjects
    );

    @Test
    void 학생별_전체_평균점수() {
        Map<String, Double> studentAvg = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getStudentName,
                        Collectors.averagingInt(TestResult::getScore)
                ));

        System.out.println(studentAvg);
    }

    @Test
    void 시험기간_과목별_평균점수() {
        Map<String, Map<String, Double>> periodSubjectAvg = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getExamPeriod,
                        Collectors.groupingBy(
                                TestResult::getSubject,
                                Collectors.averagingInt(TestResult::getScore)
                        )
                ));

        System.out.println(periodSubjectAvg);
    }

    @Test
    void 학생별_과목별_평균점수() {
        Map<String, Map<String, Double>> studentSubjectAvg = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getStudentName,
                        Collectors.groupingBy(
                                TestResult::getSubject,
                                Collectors.averagingInt(TestResult::getScore)
                        )
                ));

        System.out.println(studentSubjectAvg);
    }

    @Test
    void 시험기간_학생별_평균점수() {
        // Map<시험기간, Map<학생, 평균 점수>>로 콜렉션 구성
        Map<String, Map<String, Double>> periodStudentAvg = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getExamPeriod,
                        TreeMap::new,   // 정렬
                        Collectors.groupingBy(
                                TestResult::getStudentName,
                                Collectors.averagingInt(TestResult::getScore)
                        )
                ));

        System.out.println(periodStudentAvg);


        Map<String, Map<String, Double>> sortedPeriodStudentAvg = periodStudentAvg.entrySet()
                .stream()
                // .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,  /* ExamPeriod */
                        entry -> entry.getValue().entrySet()
                                .stream()   /* 학생 이름, 평균 점수로 구성된 스트림 StudentName, AvgScore */
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())   //comparingByKey로 하면 이름순 정렬
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey ,  //스트림 요소에서 키를 추출하는 함수
                                        Map.Entry::getValue,    //스트림 요소에서 값을 추출하는 함수
                                        (e1, e2) -> e1,     // 키 충돌 시 두 개의 충돌 데이터로 결과 데이터 1개를 만듬
                                                            // 예) (e1, e2) -> e1로 하면 기존 데이터를 유지함. e1, e2를 조합해서 새로운 데이터 생성도 가능
                                        LinkedHashMap::new  // 결과 맵의 종류
                                )),
                        (e1, e2) -> e1,
                        TreeMap::new
                ));

        System.out.println(sortedPeriodStudentAvg);
    }

    /**
     * *************** Map 특징 및 용도 설명 ******************
     * HashMap:
     * 특징: 키와 값의 저장 순서를 보장하지 않습니다. 해시 테이블을 사용하여 구현되므로 빠른 조회와 삽입 속도를 제공합니다.
     * 용도: 데이터의 순서가 중요하지 않으며 빠른 접근이 필요한 경우에 적합합니다.
     *
     * LinkedHashMap:
     * 특징: 입력된 순서(또는 접근된 순서)를 유지합니다. 해시 테이블과 이중 연결 리스트를 사용하여 구현됩니다.
     * 용도: 입력된 순서를 유지하면서도 빠른 접근이 필요한 경우에 적합합니다. LRU(Least Recently Used) 캐시 구현에도 사용됩니다.
     *
     * TreeMap:
     * 특징: 키의 자연 순서(또는 지정된 비교자(comparator)에 따른 순서)를 유지합니다. 이진 검색 트리(레드-블랙 트리)를 사용하여 구현됩니다.
     * 용도: 키가 정렬된 순서로 저장되어야 하는 경우에 적합합니다. 범위 검색과 같은 정렬된 데이터 작업이 필요한 경우 사용됩니다.
     *
     * Hashtable:
     * 특징: 동기화를 지원합니다. null 키와 null 값을 허용하지 않습니다. 레거시 클래스입니다.
     * 용도: 멀티스레드 환경에서 동기화된 데이터 접근이 필요한 경우에 적합합니다.
     *
     *  ConcurrentHashMap:
     * 특징: 동시성 제어에 최적화되어 있습니다. 여러 스레드에서 동시 읽기/쓰기가 가능합니다. null 키와 null 값을 허용하지 않습니다.
     * 용도: 고성능 멀티스레드 환경에서 사용됩니다. 동시 읽기/쓰기가 빈번한 경우 적합합니다.
     *
     *  WeakHashMap:
     * 특징: 키에 대해 약한 참조를 가지며, 가비지 컬렉터가 키를 수집할 수 있습니다. 키가 더 이상 참조되지 않으면 해당 엔트리가 자동으로 제거됩니다.
     * 용도: 캐시 구현이나 임시 데이터 저장에 적합합니다.
     *
     *  IdentityHashMap:
     * 특징: 키의 비교에 기본 equals()와 hashCode() 메서드 대신 == 연산자를 사용합니다.
     * 용도: 객체의 참조 동일성(identity)이 중요한 경우에 적합합니다. 즉, 객체의 실제 참조를 기준으로 매핑이 필요한 경우 사용됩니다.
     *
     *  EnumMap:
     * 특징: Enum 타입의 키 전용으로 사용됩니다. 매우 빠르고 효율적입니다.
     * 용도: Enum 타입의 키를 사용하는 경우에 적합합니다. Enum 상수를 기반으로 데이터를 매핑할 때 사용됩니다.
     */

    /* 점수별 점수대별 학생 평균 점수 */
    @Test
    void test() {
        //Map<이름, Map<시험기간, 평균점수>>로 컬렉션 구성
        Map<String, Map<String, Double>> studentAverages = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getStudentName,
                        Collectors.groupingBy(
                                TestResult::getExamPeriod,
                                Collectors.averagingInt(TestResult::getScore)
                        )
                ));

        System.out.println(studentAverages);


        var scoreList2PeriodName =
                studentAverages.entrySet()
                        .stream()
                        .flatMap(studentEntry ->
                                studentEntry
                                        .getValue()
                                        .entrySet()
                                        .stream()
                                        .map(examEntry ->
                                                new SimpleEntry<>(
                                                        studentEntry.getKey() + " - " + examEntry.getKey(),
                                                        examEntry.getValue())))
                        .peek(System.out::println)
                        .toList()
                        ;
//
//        // scoreList2PeriodName.forEach(System.out::println);
//
        var scoreRanges = scoreList2PeriodName
                .stream()
                .collect(Collectors.groupingBy(
                        entry -> (int) (Math.floor(entry.getValue() / 10) * 10),
                        // TreeMap::new,
                        Collectors.toList()
                ));


        System.out.println("[Range Result]");
        System.out.println(scoreRanges);

        var sortedResult = scoreRanges.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .peek(e -> System.out.println("Peek - " + e))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing((SimpleEntry<String, Double> e) -> e.getValue()).reversed())
                                //.sorted((Comparator.comparing(SimpleEntry::getValue)))
                                .collect(Collectors.toList())
                ));

        System.out.println("\n[Result]");
        System.out.println(sortedResult);

        var finalSortedResult = sortedResult.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                //.sorted(Map.Entry.<Integer, List<SimpleEntry<String, Double>>>comparingByKey().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        System.out.println("\n[Final]");
        System.out.println(finalSortedResult);

    }

    /* 점수별 점수대별 학생 평균 점수 한방에 */
    @Test
    void test2() {
        //Map<이름, Map<시험기간, 평균점수>>로 컬렉션 구성
        Map<String, Map<String, Double>> studentAverages = testResults.stream()
                .collect(Collectors.groupingBy(
                        TestResult::getStudentName,
                        Collectors.groupingBy(
                                TestResult::getExamPeriod,
                                Collectors.averagingInt(TestResult::getScore)
                        )
                ));

        System.out.println(studentAverages);

        Map<Integer, List<SimpleEntry<String, Double>>> scoreRanges =
                studentAverages.entrySet()
                        .stream()
                        .flatMap(studentEntry ->
                                studentEntry
                                        .getValue()
                                        .entrySet()
                                        .stream()
                                        .map(examEntry ->
                                                new SimpleEntry<>(
                                                        studentEntry.getKey() + " - " + examEntry.getKey(),
                                                        examEntry.getValue())))
                        // .filter(e -> e.getValue() >= 80) //필터링이 필요한 경우
                        .collect(
                                Collectors.groupingBy(
                                        entry -> (int) (Math.floor(entry.getValue() / 10) * 10),
                                        Collectors.toList()
                                )
                        );

        System.out.println("[Range Result]");
        System.out.println(scoreRanges);

        var sortedResult = scoreRanges.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .peek(e -> System.out.println("Peek - " + e))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(SimpleEntry::getValue))
                                .collect(Collectors.toList())
                ));

        System.out.println("\n[Result]");
        System.out.println(sortedResult);
    }

    /* 점수별 점수대별 학생 평균 점수 */
    @Test
    void testRefactoring() {
        //Map<이름, Map<시험기간, 평균점수>>로 컬렉션 구성
        var  sortedResult = testResults.stream()
                .collect(collectGroupByStudentNameByPeriod2Avragescore())
                .entrySet()
                .stream()
                .peek(System.out::println)
                .flatMap(flatToNameAvgScore())
                .collect(collectBy10point())
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .peek(e -> System.out.println("Peek - " + e))
                .collect(sortedByAvgscore());
        
        System.out.println("\n[Result]");
        System.out.println(sortedResult);

    }

    private static @NotNull Collector<TestResult, ?, Map<String, Map<String, Double>>> collectGroupByStudentNameByPeriod2Avragescore() {
        return Collectors.groupingBy(
                TestResult::getStudentName,
                Collectors.groupingBy(
                        TestResult::getExamPeriod,
                        Collectors.averagingInt(TestResult::getScore)
                )
        );
    }

    private static @NotNull Function<Map.Entry<String, Map<String, Double>>,
            Stream<? extends SimpleEntry<String, Double>>> flatToNameAvgScore() {
        return studentEntry ->
                studentEntry
                        .getValue()
                        .entrySet()
                        .stream()
                        .map(examEntry ->
                                new SimpleEntry<>(
                                        studentEntry.getKey() + " - " + examEntry.getKey(),
                                        examEntry.getValue()));
    }

    private static @NotNull Collector<SimpleEntry<String, Double>
            , ?, Map<Integer, List<SimpleEntry<String, Double>>>> collectBy10point() {
        return Collectors.groupingBy(
                entry -> (int) (Math.floor(entry.getValue() / 10) * 10),
                Collectors.toList()
        );
    }

    private static @NotNull Collector<Map.Entry<Integer, List<SimpleEntry<String, Double>>>,
            ?, Map<Integer, List<SimpleEntry<String, Double>>>> sortedByAvgscore() {
        return Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                        .sorted(Comparator.comparing(SimpleEntry::getValue))
                        .collect(Collectors.toList())
        );
    }


}
