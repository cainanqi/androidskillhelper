package com.cnq.androidSkillhelper.manager

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.math.roundToInt

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/6/3
 * ============================
 **/
class Timer :LifecycleObserver{
    private var times=0//定时器次数
    private var interval=0L//间隔
    private var hasDoTimes=0//已经执行的次数
    private lateinit var timeCallback: TimerCallback



    private var counter:CountDownTimer?=null

    fun start(time:Int,interval:Long,timeCallback: TimerCallback){
        Log.d("TimerTimer","=onstart")
        this.times=time
        this.interval=interval
        this.timeCallback=timeCallback
        counter=object :CountDownTimer(times*interval,interval){
            override fun onFinish() {
                timeCallback.onFinish()
                Log.d("TimerTimer","=onFinished")
            }

            override fun onTick(millisUntilFinished: Long) {
                hasDoTimes++
                timeCallback.onTick((millisUntilFinished / interval).toDouble().roundToInt(),hasDoTimes)
                Log.d("TimerTimer"," onTick=$millisUntilFinished")
                Log.d("TimerTimer"," onTick四舍五入之后=${(millisUntilFinished / interval).toDouble().roundToInt()}")
            }

        }
        counter?.start()
        timeCallback.onTick((times*interval /interval).toDouble().roundToInt(),hasDoTimes)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun cancel(){
        Log.d("TimerTimer","=oncancel")
        counter?.cancel()
        counter=null
    }

    interface TimerCallback{
        fun onTick(millisUntilFinished: Int,hasDoTimes:Int) //距离结束还有多少次
        fun onFinish()
    }
}