package com.example.javaLang.generic.lambdapattern._04factorybylambda;

import java.util.function.Function;

public enum LogType {

    FileLog("File", SaveLog.writeFile),
    DbLog("DB", SaveLog.writeDB);

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

    private class SaveLog {
        public static Function<LogType, String> writeFile = (LogType pLog) -> pLog.getKind() + " write Log to File";

        public static Function<LogType, String> writeDB = (LogType pLog) -> pLog.getKind() + " insert log to DB";
    }
}

