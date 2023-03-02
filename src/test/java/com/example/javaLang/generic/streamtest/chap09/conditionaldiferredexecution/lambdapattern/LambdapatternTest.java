package com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.lambdapattern;

import org.junit.jupiter.api.Test;

public class LambdapatternTest {

    @Test
    void test() {
        System.out.println(LogType.FileLog.save());

        System.out.println(LogType.DbLog.save());
    }

    @Test
    void methodReferenceTest() {
        var saveLog = LogType.FileLog.getSaveMethod();
        var result = saveLog.apply(LogType.FileLog);

        System.out.println(result);
    }

}
