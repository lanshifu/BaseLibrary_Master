package com.lanshifu.demo_module.network;

import com.lanshifu.baselibrary.network.ServerException;
import com.lanshifu.demo_module.bean.HongYangHttpResult;

import io.reactivex.functions.Function;

public class HongYangOpenApiRespFun <T> implements Function<HongYangHttpResult<T>, T> {

    @Override
    public T apply(HongYangHttpResult<T> httpResult) throws Exception {
        if (httpResult.code != 1) {
            //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
            throw new ServerException(httpResult.code,httpResult.msg);
        }
        //服务器请求数据成功，返回里面的数据实体
        return httpResult.data;
    }
}