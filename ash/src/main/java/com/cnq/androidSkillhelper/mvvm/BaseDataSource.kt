package com.cnq.androidSkillhelper.mvvm

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cnq.androidSkillhelper.mvvm.`interface`.DataSourceInterface
import com.cnq.androidSkillhelper.net.NetUtils2
import com.cnq.androidSkillhelper.net.retrofit.ApiService

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/26
 * 可感知生命周期的datasource
 * ============================
 **/
@Suppress("UNCHECKED_CAST")
abstract class BaseDataSource<T> : DataSourceInterface<T> {
    var apiService = NetUtils2.getInstance().apiService as T


}