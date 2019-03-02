package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DemoSdCardFindActivity extends BaseTitleBarActivity {
    @BindView(R.id.btn_search_pdf)
    Button mBtnSearchPdf;
    @BindView(R.id.btn_search_apk)
    Button mBtnSearchApk;

    File mFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //全屏，StatusBar 不显示
        //方法1
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 方法2，会有卡顿动画
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.demo_sdcard_find_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        checkSD();

        setTitleText("SD卡搜索");
    }

    @OnClick({R.id.btn_search_pdf, R.id.btn_search_apk, R.id.btn_digui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search_pdf:
                findApk();
                break;
            case R.id.btn_search_apk:
                findPdf();
                break;
            case R.id.btn_digui:
                findWithdigui();
                break;
        }
    }

    private void findWithdigui() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                long start = System.currentTimeMillis();

                List<String> result = new ArrayList<>();

                diguiFindFileWithType(mFile,".apk",result);
                Long time = System.currentTimeMillis() - start;
                ToastUtil.showShortToast("搜索完成，耗时:" + time);

                emitter.onNext(result);
                emitter.onComplete();
            }
        }).compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void _onNext(List<String> strings) {

                    }

                    @Override
                    public void _onError(String e) {

                    }
                });
    }

    boolean checkSD(){
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)){
            mFile = Environment.getExternalStorageDirectory();
            LogHelper.d(mFile.getAbsolutePath());
            return true;
        }else {
            return false;
        }
    }

    private void findApk(){
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> result = findFileWithType(mFile,".apk");
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void _onNext(List<String> strings) {

                    }

                    @Override
                    public void _onError(String e) {

                    }
                });
    }

    private void findPdf(){
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> result = findFileWithType(mFile,".pdf");
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void _onNext(List<String> strings) {

                    }

                    @Override
                    public void _onError(String e) {

                    }
                });
    }

    private List<String> findFileWithType(File root,String type){
        List<String> result = new ArrayList<>();
        if (root == null){
            return result;
        }

        int dirCount = 0;
        int fileCount = 0;
        long start = System.currentTimeMillis();
        LinkedList<File> queue = new LinkedList();
        queue.add(root);
        //广度优先，放队列
        while (!queue.isEmpty()){

            File file = queue.poll();
            File[] files = file.listFiles();
            if (files != null && files.length >0){
                for (File f : files) {
                    if (f.isDirectory()){
                        //是文件夹就加入队列
                        queue.add(f);
                        dirCount ++;
                    }else {
                        fileCount ++;
                        String fileName = f.getName();
//                                LogHelper.d("正在搜索 " + fileName);
                        if (f.getName().endsWith(type)){
                            LogHelper.d(fileName);
                            result.add(fileName);
                        }
                    }
                }

            }
        }

        Long time = System.currentTimeMillis() - start;
        ToastUtil.showShortToast("搜索完成，耗时:" + time);
        LogHelper.d("文件夹数："+dirCount);
        LogHelper.d("文件数："+fileCount);
        StringBuilder sb = new StringBuilder();
        sb.append("搜索完成，耗时:" + time);
        sb.append("\n文件夹数："+dirCount);
        sb.append("\n文件数："+fileCount);
        ToastUtil.showShortToastInThread(sb.toString());
        return result;
    }

    private void diguiFindFileWithType(File file,String type,List<String> result){
        if (file == null){
            return;
        }
        if (!file.isDirectory()){
            String fileName = file.getName();
            if (fileName.endsWith(type)){
                LogHelper.d(fileName);
                result.add(fileName);
            }
            return;
        }

        File[] files = file.listFiles();
        if (files != null && files.length >0){
            for (File f : files) {
                diguiFindFileWithType(f,type,result);
            }
        }

    }

}
