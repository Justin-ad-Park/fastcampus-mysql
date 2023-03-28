package com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature;

import java.util.Random;

public class TemperatureInfo {
    public static final Random random = new Random();

    private final String town;
    private final int temp;

    public TemperatureInfo(final String town, final int temp) {
        this.town = town;
        this.temp = temp;
    }

    public String getTown() {
        return town;
    }

    public int getTemp() {
        return this.temp;
    }

    //정적 펙토리 메소드로 특정 도시의 온도정보 인스턴스를 만든다.
    public static TemperatureInfo fetch(String town) {
        if(random.nextInt(10) == 0) //10분의 1의 확률로 에러를 발생시킨다.
            throw new RuntimeException(town + " : Temperature sensor Error!");

        return new TemperatureInfo(town, random.nextInt(0,100));
    }

    @Override
    public String toString() {
        return town + " : " + temp;
    }
}
