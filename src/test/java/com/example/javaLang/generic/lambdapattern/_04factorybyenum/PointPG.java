package com.example.javaLang.generic.lambdapattern._04factorybyenum;

import java.util.function.Supplier;

public enum PointPG {
    PGA("A-PG사", PGA_PointAPI::new),
    PGB("B-PG사", PGB_PointAPI::new);

    private String name;
    private Supplier<PointAPI> pointAPIconstructor;

    PointPG(String name, Supplier<PointAPI> constructor) {
        this.name = name;
        pointAPIconstructor = constructor;
    }

    public PointAPI getInstance() {
        return pointAPIconstructor.get();
    }
}