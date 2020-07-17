package com.cnq.testmodule

import android.util.Log
import cn.leancloud.AVOSCloud
import com.cnq.androidSkillhelper.mvvm.AbstractApplication
import com.cnq.androidSkillhelper.net.NetUtils2
import com.cnq.testmodule.service.MyApiService

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/28
 * ============================
 **/
class MyApplication : AbstractApplication(){



    override fun init() {
    }

    override fun initNetUtils() {
        Log.d("BaseDataSource", "初始化网络框架")
        NetUtils2.getInstance().init("http://pointsme.saonian.org/api/",MyApiService::class.java)
        Log.d("BaseDataSource", "apiService22是否为空=${NetUtils2.getInstance().apiService}")

    }
}