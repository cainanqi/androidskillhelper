package com.cnq.testmodule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cnq.androidSkillhelper.mvvm.BaseDataSource
import com.cnq.androidSkillhelper.net.NetUtils2
import com.cnq.androidSkillhelper.net.retrofit.ApiException
import com.cnq.androidSkillhelper.net.retrofit.ResponseResultListener
import com.cnq.testmodule.service.MyApiService
import kotlinx.coroutines.delay

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/28
 * 数据源,从网络,本地,数据库获取数据处理好交给viewmodel
 * ============================
 **/
class FirstDataSource : BaseDataSource<MyApiService> (){


    fun getCurrentTime(): LiveData<String> =
        liveData {
            while (true) {
                emit("${System.currentTimeMillis()}")
                Log.d("BaseDataSource", "发射数据")
                delay(1000)
            }
        }

    fun netWork(){
        val map = MarkBean()
        map.passportId = "2341234234234234"
        map.arrivalAt = "222222"
        Log.d("BaseDataSource", "apiService333是否为空=$apiService")

        NetUtils2.subscribe(apiService.ShoperMark("http://user.health.ngrok.saonian.org/api/v1/device/scan",map),
            object :ResponseResultListener<Any?>{
                override fun success(t: Any?) {
                    Log.d("BaseDataSource", "请求成功")
                }

                override fun failure(e: ApiException?) {
                    Log.d("BaseDataSource", "请求失败")
                }
            }
            )
    }


}