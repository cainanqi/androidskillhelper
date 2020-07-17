package com.cnq.androidSkillhelper.net.okhttp;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import androidx.annotation.WorkerThread;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 * <p>
 * 定义一个请求的拦截器：
 * <p>
 * 添加一些共同的header表头:
 * 例如:
 * 数据格式，token,CooKie等
 */
public abstract class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Map<String, String> headers = createCommonHeader();
        if (headers != null) {
            Set<Map.Entry<String, String>> stringEntry = headers.entrySet();
            for (Map.Entry<String, String> entry : stringEntry) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return chain.proceed(builder.build());
    }
    @WorkerThread
    public abstract Map<String, String> createCommonHeader();


}
