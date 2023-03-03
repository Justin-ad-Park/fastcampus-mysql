package com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.patternpractice;

import java.util.function.Function;
import static com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.patternpractice.SaveLog.*;

public enum LogType {

    FileLog("File", writeLogToFile),
    DbLog("DB", insertLogToDB);

    private final String kind;
    private Function<LogType, String> saveMethod;

    LogType(String kind, Function<LogType, String> f) {
        this.kind = kind;
        saveMethod = f;
    }

    public String getKind() {
        return kind;
    }

    public String save() {
        return saveMethod.apply(this);
    }

    //파라미터를 Enum 외부에서 넣어야 하는 경우 아래 메소드 참조를 사용
    public Function<LogType, String> getSaveMethod() {
        return saveMethod;
    }

}

