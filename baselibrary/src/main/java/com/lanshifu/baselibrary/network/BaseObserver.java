package com.lanshifu.baselibrary.network;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * rx观察者封装，只需重写成功或者失败两个方法
 * Created by lanxiaobin on 2017/9/6.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onError(Throwable e) {
        String msg = e.getMessage();
        if (e instanceof UnknownHostException) {
            msg = "网络出错";
        } else if (e instanceof SocketTimeoutException) {
            msg = "请求超时";
        } else if (e instanceof ConnectException) {
            msg = "连接失败";
        } else if (e instanceof ServerException) {
            msg = e.getMessage();
        }
        _onError("onError " + msg);

    }

    @Override
    public void onNext(T t) {
        try {
            _onNext(t);
        } catch (Exception e) {
            //onNext发送异常直接走_onError
            _onError("onNext发生异常 " + e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public abstract void _onNext(T t);

    public abstract void _onError(String e);

}
