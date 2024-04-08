package com.example.javaLang.generic.lambdapattern._04factorybylambda;

import org.junit.jupiter.api.Test;

public class LambdapatternTest {

    @Test
    void 메소드직접호출_test() {
        System.out.println(LogType.FileLog.save());

        System.out.println(LogType.DbLog.save());
    }

    @Test
    void 메서드참조_Test() {
        var saveLog = LogType.FileLog.getSaveMethod();
        var result = saveLog.apply(LogType.DbLog);

        System.out.println(result);
    }

}
