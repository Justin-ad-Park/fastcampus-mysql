package com.example.javaLang.generic.streamtest.collect.testresult;

public class TestResult {
    private String studentName;
    private String examPeriod;
    private String subject;
    private int score;

    public TestResult(String studentName, String examPeriod, String subject, int score) {
        this.studentName = studentName;
        this.examPeriod = examPeriod;
        this.subject = subject;
        this.score = score;
    }

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExamPeriod() {
        return examPeriod;
    }

    public void setExamPeriod(String examPeriod) {
        this.examPeriod = examPeriod;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}