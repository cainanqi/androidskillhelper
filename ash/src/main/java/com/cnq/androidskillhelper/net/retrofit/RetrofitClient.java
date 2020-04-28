package com.cnq.androidskillhelper.net.retrofit;

import com.cnq.androidskillhelper.net.okhttp.OkHttpProvider;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 *
 */
public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;
    static {
        instance=new RetrofitClient();
    }
    private RetrofitClient (){

    }
    public static RetrofitClient getInstance(){
        return instance;
    }
    public RetrofitClient init(String baseUrl){
       return init(baseUrl, OkHttpProvider.provideOkHttpClient());
    }
    public RetrofitClient init(String baseUrl, OkHttpClient okHttpClient){
        this.retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return this;
    }
    /**
     * 指定创建对应的ServiceInterface.防止基本的Service接口不适合
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(final Class<T> service) {
        return this.retrofit.create(service);
    }

}
