package com.cnq.androidskillhelper.device;

import android.content.Context;
import android.provider.Settings;

import javax.annotation.Nullable;


/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2019/12/15
 * ============================
 **/
public class DeviceHelper {
    private static Context appContext;

    public DeviceHelper() {
    }

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static @Nullable String getAndroidId() {
        return Settings.System.getString(appContext.getContentResolver(),Settings.Secure.ANDROID_ID);

    }
}
