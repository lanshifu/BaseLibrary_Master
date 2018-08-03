package com.lanshifu.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by newbiechen on 17-4-16.
 */

public class SPUtils {

    public static final String KEY_THEME_DAY = "theme_day";

    private static final String SHARED_NAME = "pref";
    private static SPUtils sInstance;
    private static SharedPreferences sharedReadable;
    private static SharedPreferences.Editor sharedWritable;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        sharedReadable = mContext.getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS);
        sharedWritable = sharedReadable.edit();
    }

    /**
     * put
     */

    private static void putString(String key, String value) {
        sharedWritable.putString(key, value);
        sharedWritable.apply();
    }

    public static void putInt(String key, int value) {
        sharedWritable.putInt(key, value);
        sharedWritable.apply();
    }

    public static void putBoolean(String key, boolean value) {
        sharedWritable.putBoolean(key, value);
        sharedWritable.apply();
    }

    /**
     * get
     */

    public static String getString(String key, String defValue) {
        return sharedReadable.getString(key, defValue);
    }

    public static int getInt(String key, int def) {
        return sharedReadable.getInt(key, def);
    }

    public static boolean getBoolean(String key) {
        return sharedReadable.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean def) {
        return sharedReadable.getBoolean(key, def);
    }

}
