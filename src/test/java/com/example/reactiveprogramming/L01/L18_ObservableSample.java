package com.example.reactiveprogramming.L01;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class L18_ObservableSample {

    @Test
    void ObservableTest() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                String[] datas = {"Hello, world!", "Hi, RxJava!", "Hi, Justin!"};

                for (String data : datas) {
                    if (emitter.isDisposed()) return;

                    emitter.onNext(data);
                }

                emitter.onComplete();
            }
        });

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //Do nothing
                        //Observable은 배압 기능이 없어서 요청 처리를 할 필요가 없다.
                    }

                    @Override
                    public void onNext(@NonNull String data) {
                        String threadName = Thread.currentThread().getName();

                        System.out.println(JSUtils.getNow.get() + " " + threadName + " : " + data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + " : 완료");

                    }
                });

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " : 메인 코드 끝");

        JSUtils.sleepNoEx(500L);
    }
}
