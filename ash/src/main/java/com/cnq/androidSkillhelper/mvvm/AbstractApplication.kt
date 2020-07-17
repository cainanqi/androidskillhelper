package com.cnq.androidSkillhelper.mvvm

import android.app.Application
import android.content.Context
import com.cnq.androidSkillhelper.net.NetUtils2
import com.cnq.androidSkillhelper.net.retrofit.ApiService
import com.facebook.stetho.Stetho

/**
 * Author by ${HeXinGen}, Date on 2018/10/28.
 */
abstract class AbstractApplication : Application() {
    private val TAG = "BaseApplication"
    override fun onCreate() {
        super.onCreate()
        application = this
        context = applicationContext
        init()
        Stetho.initializeWithDefaults(this)
        initNetUtils()
    }

    abstract fun init()
    abstract fun initNetUtils()

    companion object {
        var application: AbstractApplication? = null
            private set
        @JvmStatic
        var context: Context? = null
            private set

    }
}