package com.example.javaLang.generic.streamtest.chap17reactiveapplication.c_temperature;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;

import java.util.concurrent.Flow;

public class CelsiusConversionProcessor implements Flow.Processor<TemperatureInfo, TemperatureInfo> {
    private Flow.Subscriber<? super TemperatureInfo> subscriber;

    @Override
    public void subscribe(Flow.Subscriber<? super TemperatureInfo> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscriber.onSubscribe(subscription);

    }

    @Override
    public void onNext(TemperatureInfo item) {
        System.out.println("[Celsius Processor] 받은 정보 : " + item);

        TemperatureInfo celsiusInfo = new TemperatureInfo(item.getTown() + "[C]",
                (item.getTemp() - 32) * 5 / 9);

        subscriber.onNext(celsiusInfo);  //섭씨로 변환한 다음 TemperatureInfo를 다시 전송
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
