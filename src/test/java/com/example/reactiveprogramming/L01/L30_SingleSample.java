package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class L30_SingleSample {

    @Test
    void SingleSampleTest() {
        Single<DayOfWeek> single = Single.create(getDayOfWeekSingleOnSubscribe);

        single.subscribe(new SingleObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //Do Nothing
            }

            @Override
            public void onSuccess(@NonNull DayOfWeek dayOfWeek) {
                System.out.println(dayOfWeek);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }
        });

    }

    @NotNull
    private SingleOnSubscribe<DayOfWeek> getDayOfWeekSingleOnSubscribe = emitter -> {
            emitter.onSuccess(LocalDateTime.now().getDayOfWeek());
        };
}
