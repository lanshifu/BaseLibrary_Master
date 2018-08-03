package com.lanshifu.baselibrary.network;

import io.reactivex.functions.Function;

/**
 * 拦截固定格式的公共数据类型 HttpResult<T>,判断里面的状态码，
 * 如果返回code == 0 ，将HttpResult<T> 转换成  T 返回
 * Created by lanxiaobin on 2017/9/8.
 */

public class ServerResponseFunc<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> httpResult) throws Exception {
        if (httpResult.code != 0) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(httpResult.code,httpResult.message);
        }
        //服务器请求数据成功，返回里面的数据实体
        return httpResult.data;
    }
}