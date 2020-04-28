package com.cnq.androidskillhelper.net.okhttp;

import com.cnq.androidskillhelper.manager.LogPrinter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 */
public class OkHttpProvider {
    public static OkHttpClient provideOkHttpClient() {
        return  provideOkHttpClient(new HeaderInterceptor() {
            @Override
            public Map<String, String> createCommonHeader() {
                return null;
            }
        });
    }

    public static OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true) //设置出现错误进行重新连接。
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)   //拦截器
                // .addNetworkInterceptor(interceptor)//自定义网络拦截器
//                .addInterceptor(interceptor)
                // .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))//创建一个证书工厂
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        if (LogPrinter.Companion.allowLog()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //打印一次请求的全部信息
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okhttpClient = builder.build();
        return okhttpClient;
    }
}
