
package com.lanshifu.baselibrary.log;

import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.lanshifu.baselibrary.BuildConfig;
import com.lanshifu.baselibrary.utils.StorageUtil;
import com.tencent.mars.xlog.Xlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class LogHelper {
    public static boolean mIsDebugMode = true;

    private static String TAG = "lxb";
    private static int logLevel = Log.VERBOSE;


    public static String LOG_PATH_SDCARD_DIR = StorageUtil.getLogFolder() + "logs";

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;


    static {
        //腾讯xlog 需要的so
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");

        final String cachePath = LOG_PATH_SDCARD_DIR + "/cache";

        //init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, LOG_PATH_SDCARD_DIR, "MarsxLog", 0, "");
            Xlog.setConsoleLogOpen(true);

        } else {
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, LOG_PATH_SDCARD_DIR, "MarsxLog", 0, "");
            Xlog.setConsoleLogOpen(false);
        }

        com.tencent.mars.xlog.Log.setLogImp(new Xlog());

        LogHelper.d("测试xlog");
        LogHelper.d("cachePath = " + cachePath);
    }

    public static void closeLog(){
        com.tencent.mars.xlog.Log.appenderClose();
    }

    public static void trace(String str) {
        log(str, Log.DEBUG);
    }


    private static void log(String str, int level) {
        log(str, level, null);
    }

    private static void log(String logText, int level, Throwable throwable, StackTraceElement traceElement) {
        // ERROR, WARN, INFO, LOAD_LOCAL_DEBUG, VERBOSE
        if (level == Log.VERBOSE) {
            Log.v(TAG, logText);
            com.tencent.mars.xlog.Log.v(TAG, logText);
        } else if (level == Log.DEBUG) {
//            Log.d(TAG, logText);
            com.tencent.mars.xlog.Log.d(TAG, logText);
        } else if (level == Log.INFO) {
//            Log.i(TAG, logText);
            com.tencent.mars.xlog.Log.i(TAG, logText);
        } else if (level == Log.WARN) {
//            Log.w(TAG, logText);
            com.tencent.mars.xlog.Log.w(TAG, logText);
        } else if (level == Log.ERROR) {
            if (throwable != null) {
                com.tencent.mars.xlog.Log.e(TAG, logText, throwable);
            } else {
                com.tencent.mars.xlog.Log.e(TAG, logText);
            }
        }


    }

    /**
     * Log.
     *
     * @param str       the str
     * @param level     the level
     * @param throwable the throwable
     */
    private static void log(String str, int level, Throwable throwable) {
        if (mIsDebugMode) {
            if (logLevel <= level) {
                // Get the method name from the stackTrace.
                StackTraceElement[] array = Thread.currentThread().getStackTrace();
                StackTraceElement traceElement = (array != null && array.length > 5 ? array[5]
                        : array[array.length - 1]);
                log(str, level, throwable, traceElement);
            }
        }
    }

    /**
     * VERBOSE.
     *
     * @param str the str
     */
    public static void v(String str) {
        log(str, Log.VERBOSE);
    }

    /**
     * LOAD_LOCAL_DEBUG.
     *
     * @param str the str
     */
    public static void d(String str) {
        log(str, Log.DEBUG);
    }

    /**
     * WARN.
     *
     * @param str the str
     */
    public static void w(String str) {
        log(str, Log.WARN);
    }

    /**
     * INFO.
     *
     * @param str the str
     */
    public static void i(String str) {
        log(str, Log.INFO);
    }

    /**
     * ERROR.
     *
     * @param str the str
     */
    public static void e(String str) {
        log(str, Log.ERROR);
    }

    /**
     * ERROR.
     *
     * @param str       the str
     * @param throwable the throwable
     */
    public static void e(String str, Throwable throwable) {
        log(str, Log.ERROR, throwable);
    }


    public static void json(@Nullable String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }
}
