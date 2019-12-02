package com.lanshifu.baselibrary.network;


import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.progress.ProgressListener;
import com.lanshifu.baselibrary.network.progress.ProgressManager;
import com.lanshifu.baselibrary.network.progress.body.ProgressInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

import static okhttp3.internal.Util.closeQuietly;

/**
 * 封装了文件下载监听
 * Created by lanxiaobin on 2017/11/8.
 */

public abstract class DownLoadObserver<T> implements Observer<T> {

    private String mFilepath; //保存路径
    private long mRange; //保存路径

    /**
     * 封装了下载
     *
     * @param url       下载地址
     * @param filePath  保存的地址
     * @param range  断点续传位置  没有传0
     */
    public DownLoadObserver(String url, String filePath,long range) {
       this.mFilepath = filePath;
       this.mRange = range;
        LogUtil.d("lxb ->url " +url);
        ProgressManager.getInstance().addDownLoadListener(url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                //如果是断点续传，这里的进度从0开始，就不正确
                DownLoadObserver.this.onProgress(progressInfo);
            }

            @Override
            public void onError(long id, Exception e) {
                LogUtil.e("lxb ->DownLoadObserver "+e);
                onDownFailed(e.getMessage());
            }
        });
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

        ResponseBody responseBody = null;
        //保存到文件
        if(!(t instanceof ResponseBody)){
            LogUtil.e("lxb -> 传入类型非ResponseBody");
            onDownFailed("传入类型非ResponseBody");
            return;
        }
        responseBody = (ResponseBody) t;
        saveToSD(responseBody,mRange);

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("lxb ->onError "+e);
        onDownFailed(e.getMessage());

    }

    @Override
    public void onComplete() {

    }

    private void saveToSD(final ResponseBody responseBody, final long range) {
        final File file = new File(mFilepath);

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                boolean success = writeFile2Disk(responseBody, file,range);
                e.onNext(success);
                e.onComplete();
            }
        })
                .compose(RxScheduler.<Boolean>io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean success) throws Exception {
                        if (success) {
                            onDownLoadSuccess();
                        } else {
                            onDownFailed("下载失败");
                        }
                    }
                });


    }

    /**
     * 写入文件
     * @param responseBody
     * @param file
     * @param range 从哪个位置开始，默认0
     * @return
     */
    public boolean writeFile2Disk(final ResponseBody responseBody, final File file, long range) {
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        long total = range;
        long responseLength = 0;
        try {
            byte[] buf = new byte[2048];
            int len = 0;
            responseLength = responseBody.contentLength();
            inputStream = responseBody.byteStream();
            randomAccessFile = new RandomAccessFile(file, "rwd");
            if (range == 0) {
                randomAccessFile.setLength(responseLength);
            }
            randomAccessFile.seek(range);
            int progress = 0;
            long currentIime = System.currentTimeMillis();
            while ((len = inputStream.read(buf)) != -1) {
                randomAccessFile.write(buf, 0, len);
                total += len;
                progress = (int) (total * 100 / randomAccessFile.length());

                //通知太频繁,1秒发一次通知
                if (System.currentTimeMillis() - currentIime > 1000){
                    currentIime = System.currentTimeMillis();
                    LogUtil.d("保存进度 total =" + (total*100) +",randomAccessFile.length() = " +randomAccessFile.length() );
                    LogUtil.d("保存进度 " + progress);
                    LogUtil.d("保存进度 " + progress);
                    DownLoadObserver.this.onContinueDownloadProgress(false,progress);
                }
            }
            return true;
        } catch (Exception e) {
            LogUtil.e("下载失败 ：receipts " +e.getMessage());
            return false;
        } finally {
            closeQuietly(randomAccessFile);
            closeQuietly(inputStream);

        }

//        OutputStream os = null;
//        InputStream is = response.byteStream();
//        try {
//            os = new FileOutputStream(file);
//            int len;
//            byte[] buff = new byte[1024];
//
//            while ((len = is.read(buff)) != -1) {
//                os.write(buff, 0, len);
//            }
//            return true;
//
//        } catch (Exception e) {
//            LogHelper.e("lxb ->Exception :" + e);
//            return false;
//        } finally {
//            closeQuietly(os);
//            closeQuietly(is);
//        }

    }



    protected abstract void onProgress(ProgressInfo progressInfo);

    /**
     * 断电续传进度
     */
    protected abstract void onContinueDownloadProgress(boolean downloadSuccess,int progress);

    protected abstract void onDownLoadSuccess();

    protected abstract void onDownFailed(String error);

}
