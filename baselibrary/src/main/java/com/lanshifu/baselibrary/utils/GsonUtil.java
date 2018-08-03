package com.lanshifu.baselibrary.utils;

import com.google.gson.Gson;

public class GsonUtil {

    static  Gson mGson = null;
    public static Gson getGson(){
        if (mGson == null){
            mGson = new Gson();
        }
        return mGson;
    }
}
