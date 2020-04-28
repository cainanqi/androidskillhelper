package com.cnq.androidskillhelper.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.cnq.androidskillhelper.constant.LocalConstant;
import com.cnq.androidskillhelper.device.DeviceHelper;
import com.cnq.androidskillhelper.device.PackageUtils;
import com.cnq.androidskillhelper.device.UUIdUtils;
import com.cnq.androidskillhelper.framework.rxjava.SchedulerProvider;
import com.cnq.androidskillhelper.manager.SPUtils;
import com.cnq.androidskillhelper.net.okhttp.HeaderInterceptor;
import com.cnq.androidskillhelper.net.okhttp.OkHttpProvider;
import com.cnq.androidskillhelper.net.retrofit.ApiService;
import com.cnq.androidskillhelper.net.retrofit.BaseObserver;
import com.cnq.androidskillhelper.net.retrofit.ResponseResultListener;
import com.cnq.androidskillhelper.net.retrofit.Result;
import com.cnq.androidskillhelper.net.retrofit.ResultTransformer;
import com.cnq.androidskillhelper.net.retrofit.RetrofitClient;
import com.pointsme.orderipadproject.multiLanguage.LanguageHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Author by ${HeXinGen}, Date on 2018/11/2.
 */
public class NetUtils {
    private static ApiService apiService;
    //只能在application里初始化，要不然会内存泄漏
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static <T extends ApiService> void init(String base_url, Context context1, T t) {
        context = context1.getApplicationContext();
        apiService = RetrofitClient.getInstance()
                .init(base_url, OkHttpProvider.provideOkHttpClient(new RequestHeaderInterceptor()))
                .createService(t.getClass());
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public static <T> void subscribe(Observable<Result<T>> observable, ResponseResultListener<T> resultListener) {
        subscribe(observable, null, resultListener);
    }

    public static <T> void subscribe(Observable<Result<T>> observable, LifecycleTransformer<T> lifecycleTransformer, ResponseResultListener<T> resultListener) {
        Observable<T> observable1 = observable.compose(ResultTransformer.handleResult()).compose(SchedulerProvider.getInstance().applySchedulers());
        if (lifecycleTransformer != null) {
            observable1.compose(lifecycleTransformer);
        }

        observable1.subscribe(new BaseObserver<>(resultListener));
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
                header.put("X-Client-Version", PackageUtils.getVersionName(context));
                header.put("X-Client-Type", LocalConstant.client_name);
                header.put("Accept-Language", LanguageHelper.INSTANCE.getSavedLanguage(context));
                String authorization = (String) SPUtils.get(context,SPUtils.key_authorization,"");
                if (!TextUtils.isEmpty(authorization)) {
                    header.put("Authorization", authorization);
                }
            } catch (Exception e) {
                e.printStackTrace();
                header.put("X-Request-Id", UUIdUtils.createUUId());
                header.put("X-Client-Version", PackageUtils.getVersionName(context));
                header.put("X-Client-Type", LocalConstant.client_name);
                header.put("Accept-Language", LanguageHelper.INSTANCE.getSavedLanguage(context));
                String authorization = (String) SPUtils.get(context,SPUtils.key_authorization,"");
                if (!TextUtils.isEmpty(authorization)) {
                    header.put("Authorization", authorization);
                }
            }
            header.put("Courier-Api-Key", "51357171");
            return header;
        }
    }

    public static final class HttpConstants {

        //注册用户
        public static final String TYPE_REGISTER = "101";
        //忘记密码
        public static final String TYPE_FORGET_PSW = "102";
        //变更手机号
        public static final String TYPE_CHANGE_PHfONE = "103 ";
        //验证码登录verification
        public static final String TYPE_VERIFICATION_CODE_LOGIN = "104";

    }
}
