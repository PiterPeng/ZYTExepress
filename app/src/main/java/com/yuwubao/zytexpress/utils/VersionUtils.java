package com.yuwubao.zytexpress.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 版本工具类
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */

public class VersionUtils {

    private VersionUtils() {
    }

    public static boolean upperThan(int cVersion) {
        return Build.VERSION.SDK_INT >= cVersion;
    }

    public static int getVersionCode(Context c) throws PackageManager.NameNotFoundException {
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = c.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(
                c.getPackageName(), 0);
        return packInfo.versionCode;
    }

}
