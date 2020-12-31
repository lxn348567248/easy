package me.liuning.core.log;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "LogUtils";
    private static boolean enableDebug = true;

    private LogUtils() {

    }

    public static void enable(boolean enable) {
        enableDebug = enable;
    }

    public static void debug(String msg) {
        if (enableDebug) {
            Log.d(TAG, msg);
        }

    }

    public static void error(String msg) {
        if (enableDebug) {
            Log.e(TAG, msg);
        }

    }
}
