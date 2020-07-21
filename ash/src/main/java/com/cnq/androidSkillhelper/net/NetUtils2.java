package com.cnq.androidSkillhelper.net;

import android.text.TextUtils;

import com.cnq.androidSkillhelper.constant.LocalConstant;
import com.cnq.androidSkillhelper.device.DeviceHelper;
import com.cnq.androidSkillhelper.device.PackageUtils;
import com.cnq.androidSkillhelper.device.UUIdUtils;
import com.cnq.androidSkillhelper.framework.rxjava.SchedulerProvider;
import com.cnq.androidSkillhelper.manager.SPUtils;
import com.cnq.androidSkillhelper.mvvm.AbstractApplication;
import com.cnq.androidSkillhelper.net.okhttp.HeaderInterceptor;
import com.cnq.androidSkillhelper.net.okhttp.OkHttpProvider;
import com.cnq.androidSkillhelper.net.retrofit.ApiException;
import com.cnq.androidSkillhelper.net.retrofit.BaseObserver;
import com.cnq.androidSkillhelper.net.retrofit.ResponseResultListener;
import com.cnq.androidSkillhelper.net.retrofit.Result;
import com.cnq.androidSkillhelper.net.retrofit.ResultTransformer;
import com.cnq.androidSkillhelper.net.retrofit.RetrofitClient;
import com.pointsme.orderipadproject.multiLanguage.LanguageHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/29
 * ============================
 **/
public class NetUtils2<T> {
    private static NetUtils2 instance;

    public static  NetUtils2 getInstance(){
        if (instance==null){
            instance=new NetUtils2();
        }
        return instance;
    }
    private   T apiService;

    public  void init(String baseUrl, Class<T> t) {
        apiService= RetrofitClient.getInstance()
                .init(baseUrl, OkHttpProvider.provideOkHttpClient(new RequestHeaderInterceptor()))
                .createService(t);
    }

    public  T getApiService(){
        return apiService;
    }
    public static <T> void subscribe(Observable<Result<T>> observable, ResponseResultListener<T> resultListener) {
        Observable<T> observable1 = observable.compose(ResultTransformer.handleResult()).compose(SchedulerProvider.getInstance().applySchedulers());

        observable1.doOnDispose(() -> resultListener.failure(new ApiException(999,"Dispose","Dispose"))).subscribe(new BaseObserver<>(resultListener));

    }


    private final static class RequestHeaderInterceptor extends HeaderInterceptor {
        @Override
        public Map<String, String> createCommonHeader() {
            return HeaderUtils.createCommonHeader();
        }
    }

    private final static class HeaderUtils {
        /**
         * 通用的header
         *
         * @return
         */
        public static Map<String, String> createCommonHeader() {

            Map<String, String> header = new HashMap<>();
            try {
                try {
                    if (DeviceHelper.getAndroidId() == null) {
                        header.put("X-Device-Id", "this_is_a_Non-identifiable_equipment");
                    } else {
                        header.put("X-Device-Id", DeviceHelper.getAndroidId());
                    }

                } catch (Exception e) {

                }
                header.put("X-Request-Id", UUIdUtils.createUUId());
                header.put("X-Client-Version", PackageUtils.getVersionName(AbstractApplication.getContext()));
                header.put("X-Client-Type", LocalConstant.client_name);
                header.put("Accept-Language", LanguageHelper.INSTANCE.getSavedLanguage(AbstractApplication.getContext()));
//                String authorization = (String) SPUtils.get(AbstractApplication.getContext(),SPUtils.key_authorization,"");
//                if (!TextUtils.isEmpty(authorization)) {
//                    header.put("Authorization", authorization);
//                }
            } catch (Exception e) {
                e.printStackTrace();
                header.put("X-Request-Id", UUIdUtils.createUUId());
                header.put("X-Client-Version", PackageUtils.getVersionName(AbstractApplication.getContext()));
                header.put("X-Client-Type", LocalConstant.client_name);
                header.put("Accept-Language", LanguageHelper.INSTANCE.getSavedLanguage(AbstractApplication.getContext()));
//                String authorization = (String) SPUtils.get(AbstractApplication.getContext(),SPUtils.key_authorization,"");
//                if (!TextUtils.isEmpty(authorization)) {
//                    header.put("Authorization", authorization);
//                }
            }
            header.put("Courier-Api-Key", "51357171");
            return header;
        }
    }

}
