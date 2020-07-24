//package com.cnq.androidSkillhelper.net
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.provider.Settings
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.MutableLiveData
//import com.cnq.androidSkillhelper.BuildConfig
//import com.cnq.androidSkillhelper.manager.Toast
//import com.facebook.stetho.okhttp3.StethoInterceptor
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.Response
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.io.IOException
//import java.util.*
//import java.util.concurrent.TimeUnit
//
//
///**
// * @author Felix
// * @date   2019/11/29 15:28
// * Description:
// */
//
//class Rc <A>{
//
//    companion object {
//
//        private const val BASE_URL_ROOT = "http://salesman.pmall.pointsme.it/"
//        private const val BASE_URL_TEST = "http://192.168.1.110:19620/"
//
//        private var context: Context? = null
//        private lateinit var api: A
//
//        private fun getBaseUrl(): String {
//            return if (BuildConfig.DEBUG) {
//                // Debug
//                BASE_URL_ROOT
//            } else {
//                // 正式包
//                BASE_URL_ROOT
//            }
//        }
//
//        /**
//         * 初始化方法 一般在App启动时就调用
//         */
//        @SuppressLint("HardwareIds")
//        fun init(context: Context?) {
//            this.context = context
//
//            //log 打印
//            val loggingInterceptor = HttpLoggingInterceptor(
//                object : HttpLoggingInterceptor.Logger {
//                    override fun log(message: String) {
//                        Log.v("网络请求", "$message")
//                    }
//                }
//            )
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            //用户Token
//            val token = SpUtil(context!!).getString("token")
//            //请求Id
//            val userId = SpUtil(context).getString("userId")
//            //设备ID
//            val androidId: String =
//                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
//
//
//            val interceptor: Interceptor = object : Interceptor {
//                @Throws(IOException::class)
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val build = chain.request()
//                        .newBuilder() //语言
//                        .addHeader("Accept-Language", Locale.getDefault().language)
//                        .addHeader("X-Device-Id", androidId) //设备ID
//                        .addHeader("X-Request-Id", UUID.randomUUID().toString()) //请求唯一ID
//                        .addHeader("X-Client-Type", "android-app")               //android-app
//                        .addHeader("X-User-Id", userId ?: "")                        //请求Id
//                        .addHeader("Authorization", token ?: "")                     //用户Token
//                        .addHeader("X-Client-Version", BuildConfig.VERSION_CODE.toString()) //软件版本号
//                        .build()
//                    return chain.proceed(build)
//                }
//            }
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)             //设置出现错误进行重新连接。
//                .addNetworkInterceptor(StethoInterceptor()) //stetho 拦截
//                .addInterceptor(loggingInterceptor)         //log 打印
//                .build()
//
//            val retrofit: Retrofit = Retrofit.Builder()
//                .baseUrl(getBaseUrl())
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            api = retrofit.create(Api::class.java)
//        }
//
//
//        fun getApi(): Api {
//            return api
//        }
//
//        /**
//         * 封装一层请求结果
//         */
//
//        fun <T> Request(
//            owner: LifecycleOwner,
//            call: Call<RootData<T>>,
//            responseApi: ResponseApi<T>,
//            activityOrFragment: Any? = null
//        ) {
//            val successLiveData = MutableLiveData<T>()
//            val failLiveData: MutableLiveData<String> = MutableLiveData<String>()
//
//            successLiveData.observe(owner, androidx.lifecycle.Observer {
//                responseApi.success(it)
//            })
//            failLiveData.observe(owner, androidx.lifecycle.Observer {
//                responseApi.failure(it)
//            })
//            call.enqueue(object : Callback<RootData<T>> {
//                override fun onResponse(
//                    call: Call<RootData<T>>,
//                    response: retrofit2.Response<RootData<T>>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body()?.code == 40001) {
//                            //Toast.toast(context, context?.getString(R.string.loginout))
//                            //执行退出登陆
//                            //删除相关数据
//                            SpUtil(context!!).remove("token")
//                            SpUtil(context!!).remove("userId")
//                            SpUtil(context!!).remove("account")
//                            //初始化网络请求，把Authorization置空
//                            init(context?.applicationContext)
//                            //打开登陆页面 关闭当前Activity
//                            val intent = Intent()
//                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
//                            intent.action = "com.pointsme.agent.action.LOGIN_ACTIVITY"
//                            if (activityOrFragment != null) {
//                                if (activityOrFragment is Activity) {
//                                    activityOrFragment.finish()
//                                }
//                                if (activityOrFragment is Fragment) {
//                                    activityOrFragment.activity!!.finish()
//                                }
//                            }
//                            context?.startActivity(intent)
//                            return
//                        }
//
//                        if (activityOrFragment is Activity) {
//                            if (activityOrFragment.isDestroyed) {
//                                Log.i("Felix_log", "Activity销毁了 不加载数据");
//                                return
//                            }
//                        }
//                        if (activityOrFragment is Fragment) {
//                            if (activityOrFragment.isDetached) {
//                                Log.i("Felix_log", "Fragment销毁了 不加载数据");
//                                return
//                            }
//                        }
//
//                        if(response.body()?.code == 20000){
//                            successLiveData.value = response.body()?.data
//
//                        } else {
//                            context?.let { Toast.toast(it,response.body()?.msg) }
//                            failLiveData.value=response.message() + response.toString()
//
//                        }
//
//                    } else {
//                        failLiveData.value=response.message() + response.toString()
//                        context?.let { Toast.toast(it,response.body()?.msg) }
//
//                    }
//                }
//
//                override fun onFailure(call: Call<RootData<T>>, t: Throwable) {
//                    failLiveData.value = t.message
//                    context?.let { Toast.toast(it,t.message) }
//                }
//            })
//        }
//    }
//}
