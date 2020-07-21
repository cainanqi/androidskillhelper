package com.cnq.androidskillhelper.device;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Author by ${HeXinGen}, Date on 2018/11/2.
 */
public class PackageUtils {
    /**
     * 获取app的Version
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "versionName_unknown";
        }
    }

    public static String getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            return "versionCode_unknown";
        }
    }

    public static String getPkgName(Context context) {
        return context.getPackageName();
    }
}
