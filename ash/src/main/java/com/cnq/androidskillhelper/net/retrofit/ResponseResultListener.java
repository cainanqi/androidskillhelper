package com.cnq.androidskillhelper.net.retrofit;

/**
 * Author by ${HeXinGen}, Date on 2018/11/2.
 */
public interface ResponseResultListener<T> {
    /**
     * 结果的回调
     * @param t
     */
    void success(T t);
    /**
     * 异常的回调
     * @param e
     */
    void failure(ApiException e);
}

