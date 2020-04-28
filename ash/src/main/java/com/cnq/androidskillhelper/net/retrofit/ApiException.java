package com.cnq.androidskillhelper.net.retrofit;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 * <p>
 * 自定义异常
 */
public class ApiException extends Exception {
    public static final int error_un_known = 1111;
    public static final int error_parse = 1112;
    public static final int error_net = 1113;
    public static final int error_http = 1114;
    public static final int error_response_bean_empty =115;
    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    /**
     * 处理类： 针对网络异常和解析异常和一些未知异常
     */
    public static final  class ExceptionHandler{
        public static ApiException handlerException(Throwable e) {
            ApiException ex;
            if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                //解析错误
                ex = new ApiException(ApiException.error_parse, e.getMessage());
                return ex;
            } else if (e instanceof UnknownHostException
                    || e instanceof SocketTimeoutException
                    || e instanceof ConnectException) {
                //连接错误或者网络错误
                ex = new ApiException(ApiException.error_net, e.getMessage());
                return ex;
            } else {
                //未知错误
                ex = new ApiException(ApiException.error_un_known, e.getMessage());
                return ex;
            }
        }
    }
}
