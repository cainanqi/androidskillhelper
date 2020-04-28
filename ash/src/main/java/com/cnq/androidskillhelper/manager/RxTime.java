package com.cnq.androidskillhelper.manager;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020-4-29
 * ============================
 **/
public class RxTime {
    private Disposable mDisposable;
    private boolean mStop;
    private static RxTime ru;


    public static RxTime getInstance() {
        if (ru != null) {
            return ru;
        } else {
            ru = new RxTime();
            return ru;
        }

    }

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */


    public void timer(long milliseconds, final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void interval(long milliseconds, final IRxNext next) {
        mStop = false;
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .takeWhile(aLong -> {
                    Log.d("intervallll", "long=" + aLong);
                    return !mStop;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 执行指定次数的定时器
     *
     * @param milliseconds
     * @param next
     * @param times
     */
    public void intervalInTimes(long milliseconds, int times, final IRxNextWithCompleted next) {
        mStop = false;
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .takeWhile(aLong -> {
                    System.out.println("intervalInTimes=" + aLong);
                    if (aLong > times - 1) {
                        mStop = true;
                    }
                    return !mStop;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        System.out.println("intervalInTimes=number=" + number);
                        if (next != null) {
                            next.doNext(number);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (next != null) {
                            next.completed();
                        }
                    }
                });
    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mStop = true;
    }

    public interface IRxNext {
        void doNext(long number);
    }
    //带完成返回的
    public interface IRxNextWithCompleted {
        void doNext(long number);
        void completed();
    }
}
