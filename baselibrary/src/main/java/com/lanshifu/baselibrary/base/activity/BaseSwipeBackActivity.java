package com.lanshifu.baselibrary.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.swipeback.SwipeBackHelper;

public abstract class BaseSwipeBackActivity extends BaseActivity {

    private SwipeBackHelper mSwipeBackHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportSwipeBack(R.color.colorPrimary);

        setStatusBarColor();

    }


    /**
     * 开启侧滑返回
     */
    public void supportSwipeBack(int color) {
        if (mSwipeBackHelper == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwipeBackHelper = new SwipeBackHelper(this);
            if (color >>> 24 <= 0) {
                color = getResources().getColor(R.color.colorWindowBackground);
            }
//            //设置窗口背景颜色，覆盖不可见区域出现的黑色（不可见区域常见为当输入法及导航栏变化时的背景）
            mSwipeBackHelper.setWindowBackgroundColor(color | 0XFF000000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

}
