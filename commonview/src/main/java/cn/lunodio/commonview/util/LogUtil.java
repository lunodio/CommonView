package cn.lunodio.commonview.util;

import android.util.Log;

public class LogUtil {
    public static final String TAG = LogUtil.class.getSimpleName();

    public static final int LOG_NONE = 0;
    public static final int LOG_DEBUG = 3;
    public static final int LOG_INFO = 4;
    public static final int LOG_WARN = 5;
    public static final int LOG_ERROR = 6;

    private static int logLevel = LOG_INFO;
    private  static final   boolean intercept = false;

    public static boolean isIntercept() {
        return intercept;
    }


    public static void onSuccess() {
        i("成功");
    }

    public static void onFailure(Exception e) {
        e("例外" + e);
    }


    public static void i(String s) {
        if (intercept) return;
        i(TAG, '\n' + s);
    }

    public static void i(String TAG, String s) {
        if (intercept) return;
        Log.i(TAG, '\n' + s + '\n');
    }

    public static void w(String msg) {
        if (intercept) return;

        w(TAG, msg);
    }

    public static void w(String TAG, String msg) {
        if (intercept) return;

        Log.w(TAG, msg);
    }

    public static void d(String s) {
        if (intercept) return;

        d(TAG, '\n' + s);
    }

    public static void d(String TAG, String s) {
        if (intercept) return;

        Log.d(TAG, "\n" + s);
    }

    public static void e(String s) {        if (intercept) return;

        e(TAG, '\n' + s);
    }

    public static void e(String TAG, String s) {
        if (intercept) return;

        Log.e(TAG, "\n" + s);
    }

    public static int getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(int logLevel) {
        LogUtil.logLevel = logLevel;
    }
}
