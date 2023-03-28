package com.example.javaLang.generic.streamtest.chap17reactiveapplication.e_RxJava_observable;

import com.example.javaLang.generic.streamtest.chap17reactiveapplication.a_temperature.TemperatureInfo;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class TemperatureGenerator {

    public static Observable<TemperatureInfo> getTemperature(String town) {
        return Observable.create(emitter ->
                Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe(i ->  {
                        if(emitter.isDisposed() || i >= 5) {    //폐기되었거나, 5회 이상 수신했으면 종료
                           emitter.onComplete();
                        }

                        try {
                            emitter.onNext(TemperatureInfo.fetch(town));
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    })
        );
    }
}
