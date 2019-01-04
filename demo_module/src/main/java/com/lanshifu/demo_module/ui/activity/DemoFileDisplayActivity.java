package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.widget.FileDisplayView;

import java.io.File;

import butterknife.BindView;

/**
 * Created by lanshifu on 2018/12/12.
 */

public class DemoFileDisplayActivity extends BaseTitleBarActivity {
    @BindView(R.id.file_display_view)
    FileDisplayView mFileDisplayView;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_file_display_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {



        Intent intent = this.getIntent();
        String fileName = intent.getStringExtra("name");
        String filePath = intent.getStringExtra("path");
        setTitleText(fileName);
        if (TextUtils.isEmpty(filePath)) {
            showShortToast("文件路径为空");
            return;
        }

        if (filePath.contains("http")) {//网络地址要先下载
            showErrorToast("需要下载才能使用");
        } else {
            mFileDisplayView.displayFile(new File(filePath));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFileDisplayView != null){
            mFileDisplayView.onStop();
        }
    }
}
