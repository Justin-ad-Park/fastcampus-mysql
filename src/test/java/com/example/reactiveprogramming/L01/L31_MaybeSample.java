package com.example.reactiveprogramming.L01;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class L31_MaybeSample {

    @Test
    void MaybeSampleTest() {
        Maybe<DayOfWeek> maybe = Maybe.create(getDayOfWeekSingleOnSubscribe);

        maybe.subscribe(new MaybeObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //Do Nothing
            }

            @Override
            public void onSuccess(@NonNull DayOfWeek dayOfWeek) {
                System.out.println("Maybe onSuccess : " + dayOfWeek);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("완료");
            }
        });

    }

    @NotNull
    private MaybeOnSubscribe<DayOfWeek> getDayOfWeekSingleOnSubscribe = emitter -> {
            emitter.onSuccess(LocalDateTime.now().getDayOfWeek());
        };
}
