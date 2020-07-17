package com.cnq.testmodule.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.cnq.androidSkillhelper.mvvm.BaseDataSource
import com.cnq.androidSkillhelper.mvvm.BaseLiveDataViewModel
import com.cnq.testmodule.FirstDataSource
import com.cnq.testmodule.service.MyApiService
import kotlinx.coroutines.launch

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/28
 * ============================
 **/
class FirstViewModel :BaseLiveDataViewModel<MyApiService,FirstDataSource>(){


    val currentTime1=mDataSource.getCurrentTime()
    fun onRefresh() {
     //  showLoading("111")
        mDataSource.netWork()
    }

    override fun initDataSource(): FirstDataSource {
        return FirstDataSource()
    }


}