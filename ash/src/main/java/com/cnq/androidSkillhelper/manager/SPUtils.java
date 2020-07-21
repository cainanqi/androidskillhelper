package com.cnq.androidskillhelper.manager;

/**
 * Created by john on 2016/7/25.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;


import com.tencent.mmkv.MMKV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    public static void initialize(Context context) {
        String rootDir = MMKV.initialize(context);

    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {


        if (object instanceof String) {
            MMKV.defaultMMKV().encode(key, (String) object);
        } else if (object instanceof Integer) {
            MMKV.defaultMMKV().encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            MMKV.defaultMMKV().encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            MMKV.defaultMMKV().encode(key, (Float) object);
        } else if (object instanceof Long) {
            MMKV.defaultMMKV().encode(key, (Long) object);
        } else if (object instanceof Double) {
            MMKV.defaultMMKV().encode(key, (Double) object);
        } else if (object instanceof Parcelable) {
            MMKV.defaultMMKV().encode(key, (Parcelable) object);
        } else {
            MMKV.defaultMMKV().encode(key, object.toString());
        }

    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static <T> T get(String key, T defaultObject) {


        if (defaultObject instanceof String) {
            return (T) MMKV.defaultMMKV().decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return (T) (Integer) MMKV.defaultMMKV().decodeInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return (T) (Boolean) MMKV.defaultMMKV().decodeBool(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return (T) (Float) MMKV.defaultMMKV().decodeFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return (T) (Long) MMKV.defaultMMKV().decodeLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Double) {
            return (T) (Double) MMKV.defaultMMKV().decodeDouble(key, (Long) defaultObject);
        }

        return null;
    }


}