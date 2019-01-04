package com.lanshifu.baselibrary.utils;

import com.lanshifu.baselibrary.log.LogHelper;

import java.lang.reflect.ParameterizedType;

/**
 * 类转换初始化
 */
public class TUtil {


    /**
     * 获取直接超类的 Type，getActualTypeArguments()返回参数数组。
     * @return
     */

    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (Exception e) {
            LogHelper.e(e.getMessage());
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
