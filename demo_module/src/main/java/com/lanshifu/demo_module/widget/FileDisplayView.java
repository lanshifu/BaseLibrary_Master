package com.lanshifu.demo_module.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lanshifu.baselibrary.utils.ToastUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;


public class FileDisplayView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private static String TAG = "FileDisplayView";
    private TbsReaderView mTbsReaderView;
    private Context context;

    public FileDisplayView(Context context) {
        this(context, null, 0);
    }

    public FileDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }


    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    /**
     * 传 file
     * @param file
     */
    public void displayFile(File file) {

        if (file != null && !TextUtils.isEmpty(file.toString())) {
            //加载文件
            Bundle localBundle = new Bundle();
            localBundle.putString("filePath", file.toString());
            localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");
            if (this.mTbsReaderView == null)
                this.mTbsReaderView = getTbsReaderView(context);
            boolean bool = this.mTbsReaderView.preOpen(getFileType(file.toString()), false);
            if (bool) {
                this.mTbsReaderView.openFile(localBundle);
            }else {
                ToastUtil.showShortToast("是不是出错了");
            }
        } else {
            Log.e("", "文件路径无效！");
        }

    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null");
            return str;
        }
        Log.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.d(TAG, "onCallBackAction " + integer);
    }

    /**
     * ondestroy 调用
     */
    public void onStop() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
