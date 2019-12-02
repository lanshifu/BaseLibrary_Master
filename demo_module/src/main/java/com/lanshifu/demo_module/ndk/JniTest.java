package com.lanshifu.demo_module.ndk;

import com.lanshifu.baselibrary.log.LogUtil;

/**
 * Created by lanshifu on 2018/12/2.
 */

public class JniTest {

    static  {
        /** 加载 libdemo_module.so */
        System.loadLibrary("demo_module");
    }

    /**
     * 声明本地方法
     * @return
     */
    public native static String get();

    public native static void set(String test);

    public static void methodCallByJni(String msgFromJni){
        LogUtil.d("jni 调用java方法 " + msgFromJni);
    }

    public static void methodCallByJni(){
        LogUtil.d("jni 调用java void无参方法 ");
    }


}
