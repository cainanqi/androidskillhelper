package com.cnq.testmodule.viewmodel

import com.cnq.androidskillhelper.mvvm.BaseLiveDataViewModel
import com.cnq.testmodule.FirstDataSource

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/28
 * ============================
 **/
class FirstViewModel : BaseLiveDataViewModel<FirstDataSource>(){


    val currentTime1=mDataSource.getCurrentTime()
    fun onRefresh() {
     //  showLoading("111")
        mDataSource.netWork()
    }

    override fun initDataSource(): FirstDataSource {
        return FirstDataSource()
    }


}