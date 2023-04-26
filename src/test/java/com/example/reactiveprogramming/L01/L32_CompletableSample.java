package com.example.reactiveprogramming.L01;

import com.example.javaLang.generic.basic.JSUtils;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class L32_CompletableSample {

    @Test
    void MaybeSampleTest() {
        Completable completable = Completable.create(getDayOfWeekSingleOnSubscribe);

        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //Do Nothing
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

        JSUtils.sleepNoEx(100L);
    }

    @NotNull
    private CompletableOnSubscribe getDayOfWeekSingleOnSubscribe = emitter -> {
            emitter.onComplete();
        };
}
