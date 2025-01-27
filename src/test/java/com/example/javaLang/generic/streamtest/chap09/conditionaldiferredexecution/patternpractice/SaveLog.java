package com.example.javaLang.generic.streamtest.chap09.conditionaldiferredexecution.patternpractice;

import java.util.function.Function;

public class SaveLog {
    public static Function<LogType, String> writeLogToFile = (LogType pLog) -> pLog.getKind() + " write Log to File";

    public static Function<LogType, String> insertLogToDB = (LogType pLog) -> pLog.getKind() + " insert log to DB";
}
