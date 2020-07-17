package com.cnq.androidSkillhelper.net.retrofit;

/**
 * Author by ${HeXinGen}, Date on 2018/9/19.
 */
public class Result<T> {
    private int code;   //状态标识
    private T data;    //具体实体
    private String msg;  //该接口特殊说明

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
