package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

import org.jetbrains.annotations.NotNull;

public class ResultFactories {

    private static <R, E extends Throwable> ResultFactory<R, E> createInstance(ResultSuccessFactory<R> successFactory,
                                                       ResultFailureFactory<R, E> failureFactory) {
        return new ResultFactory<>() {
            @Override
            public R success(String value) {
                return successFactory.create(value);
            }

            @Override
            public R failure(E e) {
                return failureFactory.create(e);
            }
        };
    }

    public static <E extends Throwable> ResultFactory<ResultClass<E>, E> createResultClass() {
        ResultFactory<ResultClass<E>, E> instance = createInstance(ResultClass::success, ResultClass::failure);
        return instance;
    }

    public static <E extends Throwable> ResultFactory<ResultRecord<E>, E> createResultRecord() {
        ResultFactory<ResultRecord<E>, E> instance = createInstance(ResultRecord::success, ResultRecord::failure);
        return instance;
    }

    public static <R> @NotNull Boolean isSuccess(R r) {
        if (r instanceof ResultClass) {
            return ((ResultClass) r).isSuccess();
        } else if (r instanceof ResultRecord) {
            return ((ResultRecord) r).isSuccess();
        }
        throw new IllegalArgumentException("Unknown result type: " + r.getClass());
    }

}
