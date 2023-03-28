package com.example.javaLang.generic.streamtest.chap17reactiveapplication.f_RxJava_observableEx;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import com.example.javaLang.generic.streamtest.chap17reactiveapplication.e_RxJava_observable.TemperatureGenerator;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CelsiusTemeratureGenerator {
    public static Observable<TemperatureInfo> getCelsiusTemperatures(String town) {
        return TemperatureGenerator.getTemperature(town)
            .map(temp -> new TemperatureInfo(temp.getTown(), (temp.getTemp() - 32) * 5/9)
        );
    }

    public static Observable<TemperatureInfo> getNegativeTemperature(String town) {
        return getCelsiusTemperatures(town)
                .filter(temp -> temp.getTemp() < 0);
    }

    public static Observable<TemperatureInfo> getCelsiusTemperatures(String... towns) {
        return Observable.merge(Arrays.stream(towns)
            .map(CelsiusTemeratureGenerator::getCelsiusTemperatures)
            .collect(Collectors.toList()));
    }


}