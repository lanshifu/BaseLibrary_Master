package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.demo_module.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lanshifu on 2018/10/29.
 */

public class DemoRxjavaActivity extends BaseTitleBarActivity {

    private Disposable mDisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("rxjava2 一些常用操作符");

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogHelper.d("ObservableOnSubscribe--subscribe");
                LogHelper.d("ObservableOnSubscribe--发射数据...");
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onComplete();
                LogHelper.d("ObservableOnSubscribe--数据发射完成");
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogHelper.d("Observer--onSubscribe");
            }

            @Override
            public void onNext(String s) {
                LogHelper.d("Observer--onNext：" + s);
            }

            @Override
            public void onError(Throwable e) {
                LogHelper.d("Observer--onError");
            }

            @Override
            public void onComplete() {
                LogHelper.d("Observer--onComplete");
            }
        });

        Observable.just("1","2")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(s);
                            }
                        });
                    }
                });

        String str = "111";
        Integer i = 1;
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.reverse();




    }


    /**
     * 善用 zip 操作符，实现多个接口数据共同更新 UI
     */
    private void zip() {
        //
        Observable<Integer> observable1 = Observable.just(1);
        Observable<String> observable2 = Observable.just("1");

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {

                return integer.toString() + s;
            }
        }).compose(RxScheduler.io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    /**
     * 采用 interval 操作符实现心跳间隔任务
     */
    private void interval() {
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                })
                .compose(RxScheduler.io_main())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        // Customization and Branding
    }
}
