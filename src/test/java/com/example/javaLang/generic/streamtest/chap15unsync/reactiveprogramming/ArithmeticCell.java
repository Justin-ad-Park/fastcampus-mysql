package com.example.javaLang.generic.streamtest.chap15unsync.reactiveprogramming;

public class ArithmeticCell extends SimpleCell {
    private int left;
    private int right;


    public ArithmeticCell(String name) {
        super(name);
    }

    public void setLeft(int left) {
        this.left = left;
        notificationAll();
    }

    public void setRight(int right) {
        this.right = right;
        notificationAll();
    }

    private void notificationAll() {
        onChange(this.left + this.right);
    }
}
