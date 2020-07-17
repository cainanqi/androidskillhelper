package com.cnq.androidSkillhelper.net.retrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 */
public class BaseObserver<T> implements Observer<T> {
    private ResponseResultListener<T> listener;
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());


    public BaseObserver(ResponseResultListener<T> listener, Context instance, Class activity) {
        this.listener = listener;

    }

    public BaseObserver(ResponseResultListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T result) {
        if (listener != null) {
            mainHandler.post(() -> {
                getListener().success(result);
            });
        }
    }

    @Override
    public void onError(Throwable t) {
        if (getListener() != null) {
            mainHandler.post(() -> {
                if (t instanceof ApiException) {
                    ApiException apiException = (ApiException) t;
                    if (apiException.getCode() == ApiException.error_response_bean_empty) {
                        getListener().success(null);
                    } else {
                        if (apiException.getDisplayMessage() == null) {
                            apiException.setDisplayMessage("");
                        }

                        //错误码
                       // apiException.getCode()

                        getListener().failure(apiException);

                    }
                } else {
                    getListener().failure(ApiException.ExceptionHandler.handlerException(t));

                }
            });
        }
    }

    @Override
    public void onComplete() {

    }

    private ResponseResultListener<T> getListener() {
        return this.listener;
    }
}
