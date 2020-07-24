package com.cnq.androidSkillhelper.mvvm.interfaces

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/26
 * ============================
 **/
interface  DataSourceInterface : LifecycleObserver {

 @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
 public fun doOnCreate() {

  Log.d("BaseDataSource", "ON_CREATE")
 }

 @OnLifecycleEvent(Lifecycle.Event.ON_START)
 fun doOnStart() {
  Log.d("BaseDataSource", "ON_START")
 }

 @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
 fun doOnResume() {
  Log.d("BaseDataSource", "ON_RESUME")
 }

 @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
 fun doOnPause() {
  Log.d("BaseDataSource", "ON_PAUSE")
 }

 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
 fun doOnDestroy() {
  Log.d("BaseDataSource", "ON_DESTROY")
 }
}