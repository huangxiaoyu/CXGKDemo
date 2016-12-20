package com.cxgk.app.cxgkdemo.utils;

import android.util.Log;

/**
 * Created by Huang on 2016/3/22.
 */
public class LogUtils {


    public static boolean isDebug = true;
    private static final String TAG = "huang_kaiyeoa_log";

    public LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void isDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static void v(String str) {
        if (isDebug) {
            Log.v(TAG, str);
        }
    }

    public static void d(String str) {
        if (isDebug) {
            Log.d(TAG, str);
        }
    }

    public static void i(String str) {
        if (isDebug) {
            Log.i(TAG, str);
        }
    }

    public static void e(String str) {
        if (isDebug) {
            Log.e(TAG, str);
        }
    }

    public static void v(String TAG, String str) {
        if (isDebug) {
            Log.v(TAG, str);
        }
    }

    public static void d(String TAG, String str) {
        if (isDebug) {
            Log.d(TAG, str);
        }
    }

    public static void i(String TAG, String str) {
        if (isDebug) {
            Log.i(TAG, str);
        }
    }

    public static void e(String TAG, String str) {
        if (isDebug) {
            Log.e(TAG, str);
        }
    }
}
