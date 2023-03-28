package com.example.javaLang.generic.streamtest.chap17reactiveapplication.e_RxJava_observable;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class TemperatureObserver implements Observer<TemperatureInfo> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull TemperatureInfo temperatureInfo) {
        System.out.println("TempInfo - " + temperatureInfo.getTown() + " : " + temperatureInfo.getTemp());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        System.err.println("Got problem: " + e.getMessage() );
    }

    @Override
    public void onComplete() {
        System.out.println("Observer Done!");
    }
}
