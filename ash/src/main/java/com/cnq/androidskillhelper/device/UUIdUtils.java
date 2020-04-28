package com.cnq.androidskillhelper.device;

import java.util.UUID;

/**
 * Author by ${HeXinGen}, Date on 2018/11/2.
 */
public class UUIdUtils {
    /**
     * 创建一个随机的数，用于当作订单或者请求的标识
     * @return
     */
    public static String createUUId(){
        return UUID.randomUUID().toString();
    }
}
