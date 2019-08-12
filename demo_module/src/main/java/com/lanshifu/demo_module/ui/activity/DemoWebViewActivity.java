package com.lanshifu.demo_module.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;

import butterknife.BindView;

/**
 * Created by lanshifu on 2018/11/7.
 */

public class DemoWebViewActivity extends BaseTitleBarActivity {
    @BindView(R2.id.webview)
    WebView mWebview;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_webview_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("webView");

        //允许JavaScript执行
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebChromeClient(new WebChromeClient());
        //找到Html文件，也可以用网络上的文件
        mWebview.loadUrl("file:///android_asset/demo.html");
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        mWebview.addJavascriptInterface(new JsObject(), "android");

        findViewById(R.id.btn_call_js).setOnClickListener(v -> {

            /**
             * 调用js代码，无返回值和有返回值
             */
            mWebview.evaluateJavascript("alertMessage(\"来自android\")",null);
            mWebview.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    ToastUtil.showShortToast("调用js " + value);
                }
            });

        });
    }


    private final class JsObject {

        @JavascriptInterface  //一定要写这个注解
        public void toast(String str){
            ToastUtil.showShortToast("h5点击 " + str);
        }
    }

    @Override
    protected void onPause() {
        mWebview.onPause();
        mWebview.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWebview != null) {
            mWebview.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        mWebview.onResume();
        mWebview.resumeTimers();
        super.onResume();
        if (mWebview != null) {
            mWebview.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebview != null){
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}
