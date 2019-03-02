package com.lanshifu.demo_module.mvp.presenter;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.HttpResult;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.demo_module.mvp.view.DemoMainView;
import com.lanshifu.demo_module.network.DemoApi;
import com.lanshifu.demo_module.receiver.ConfigReceiver;
import com.lanshifu.demo_module.test.ProxyDemo;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DemoMainPresenter extends BasePresenter<DemoMainView> {

    public void test() {
        ProxyDemo dynamicFactory = new ProxyDemo();
        dynamicFactory.test();
        mView.textResult("哈哈哈");

        Integer.valueOf(10);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    //只能更新单个文件，文件夹无效
    public void updateMedia() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa/胖妞.gif";
        LogHelper.d("path = " + path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(mContext, new String[]{path}, null,
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        @Override
                        public void onMediaScannerConnected() {
                            ToastUtil.showShortToast("onMediaScannerConnected");
                        }

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            ToastUtil.showShortToast("onScanCompleted");
                        }
                    });
        } else {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(new File(path))));
        }

        UIUtil.snackbarText("刷新媒体库中，请稍后查看");
    }

    public void request() {
        RetrofitHelper.getInstance().createApi(DemoApi.class)
                .request(1, 10, 0)
                .compose(RxScheduler.io_main_lifecycle((LifecycleProvider<ActivityEvent>) mView))
                .subscribe(new BaseObserver<HttpResult>() {
                    @Override
                    public void _onNext(HttpResult result) {
                        ToastUtil.showShortToast(result.message);
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast(e);
                    }
                });
    }



    public void initConfig() {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.suntek.mway.carrier_configuation",
                    "com.suntek.mway.carrier_configuation.MainService");
            mContext.startService(intent);
            LogHelper.d("启动服务");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class Parent {

    }

    class Self extends Parent {

    }

    class Son extends Self {
    }

    void method() {

        // 上界<? extends T> 不能往里存，只能往外取
        List<? extends Self> list1 = new ArrayList<Son>();
//        list1.add(new Self());
        Object self1 = list1.get(0);
        Parent self2 = list1.get(0);


        //下界<? super T>不影响往里存，但往外取只能放在Object对象里
        List<? super Self> list2 = new ArrayList<Self>();
        list2.add(new Son());
        list2.add(new Self());
//        list2.add(new Parent());
//        Self self3 = list2.get(0);
        Object self4 = list2.get(0);

    }

    public void test_thread() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                LogHelper.d("线程1：" + Thread.currentThread().getName());
            }
        };
        thread1.start();
        thread1.run();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LogHelper.d("线程2：" + Thread.currentThread().getName());
            }
        });
        thread2.start();
        thread2.run();

        //这种方式可以获得线程执行完之后的返回值
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                LogHelper.d("线程3：" + Thread.currentThread().getName());
                return null;
            }
        });
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        thread3.run();

        SemaphoreTest();
    }

    public void SemaphoreTest() {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    doSomething();
                }
            }.start();
        }

    }

    //Semaphore 控制并发数量
    private Semaphore mSemaphore = new Semaphore(2);

    private void doSomething() {
        try {
            mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogHelper.d(Thread.currentThread().getName() + " 进来了");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogHelper.d(Thread.currentThread().getName() + " 出去了");
        mSemaphore.release();
    }


    public void rxjavaTest() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogHelper.d("subscribe 当前线程" + Thread.currentThread());
                Thread.sleep(5000);
                emitter.onNext(123);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        LogHelper.d("apply 当前线程 " + Thread.currentThread());
                        //这里打印的是 RxCachedThreadScheduler ，说明subscribeOn 只有第一次有效
                        return integer.toString();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogHelper.d("onSubscribe 当前线程 " + Thread.currentThread());

                    }

                    @Override
                    public void onNext(String s) {
                        LogHelper.d("onNext 当前线程 " + Thread.currentThread());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogHelper.d("onComplete 当前线程 " + Thread.currentThread());

                    }
                });

        Flowable.just("")
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void blockCanaryTest(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    ConfigReceiver receiver = new ConfigReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.suntek.mway.carrier_configuation.intent.action.CARRIER_CONFIG_CHANGED");
        mContext.registerReceiver(receiver, filter);
    }

    @Override
    public void onDestory() {
        super.onDestory();
        mContext.unregisterReceiver(receiver);
    }
}
