package com.example.javaLang.generic;

public class Util {
    public static <T> Box<T> boxing(T t) {
        Box<T> box = new Box<>();
        box.set(t);
        return box;
    }

    public static <M extends String, N extends String, R extends String> Box<R> getBox(M model, N name) {
        Box<R> box = new Box<R>();
        box.set((R)model);

        return box;
    }

    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        if (p1.getKey().equals(p2.getKey()) == false) return false;

        if (p1.getValue().equals(p2.getValue()) == false) return false;

        return true;
    }

    public static <T extends Number> int compare(T t1, T t2) {
        double v1 = t1.doubleValue();
        double v2 = t2.doubleValue();

        return Double.compare(v1, v2);
    }

    public static <T extends String> boolean compare(T t1, T t2) {
        return t1.equals(t2);
    }

    public static <T extends String, M extends Number> boolean compare(T t1,  M t2) {
        String t2s = t2.toString();
        System.out.println("t2" + t2s);
        return t1.equals(t2.toString());
    }
}
