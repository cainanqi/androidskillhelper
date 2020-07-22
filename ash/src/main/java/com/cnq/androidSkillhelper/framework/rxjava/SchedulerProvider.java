package com.cnq.androidSkillhelper.framework.rxjava;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 */
public class SchedulerProvider implements BaseSchedulerProvider {
    private static BaseSchedulerProvider instance;
    static {
          instance=new SchedulerProvider();
    }
    private SchedulerProvider(){

    }
    public  static BaseSchedulerProvider getInstance(){
        return instance;
    }
    @Override
    public Scheduler createIOThread() {
        return Schedulers.io();
    }
    @Override
    public Scheduler createUIThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler createComputationThread() {
        return Schedulers.computation();
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable-> observable.subscribeOn(createIOThread()).observeOn(createUIThread());
    }
}
