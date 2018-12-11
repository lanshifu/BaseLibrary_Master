package com.lanshifu.baselibrary.base.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.lanshifu.baselibrary.swipeback.SwipeBackGesture;

public abstract class BaseSwipeBackActivity extends BaseActivity {

    /**
     * 触摸工具类
     */
    private SwipeBackGesture mSwipeBackGesture;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 手势工具类
        mSwipeBackGesture = new SwipeBackGesture(this);

    }



    //==============================================================================================
    //======================= 以 下 是 关 于 手 势 右 滑 关 闭 ========================================
    //==============================================================================================

    /**
     * 绑定手势
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != mSwipeBackGesture && mSwipeBackGesture.motionEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开启滑动关闭界面
     *
     * @param open
     */
    protected void openSlideFinish(boolean open) {
        if (mSwipeBackGesture == null) {
            return;
        }
        mSwipeBackGesture.openSlideFinish(open);
    }

    /**
     * 抬起关闭
     *
     * @param upFinish 【true：手指抬起后再关闭页面】
     *                 【false：进度条圆满就立刻关闭页面】
     */
    public void setUpFinish(boolean upFinish) {
        if (mSwipeBackGesture == null) {
            return;
        }
        mSwipeBackGesture.setUpFinish(upFinish);
    }

    /**
     * 设置进度条颜色
     */
    public void setProgressColor(int color) {
        if (mSwipeBackGesture != null)
            mSwipeBackGesture.setProgressColor(color);
    }

    /**
     * 滑动View
     * 【滑动过程中会移动的View】
     */
    public void setMoveView(View SlideView) {
        mSwipeBackGesture.setRootView(SlideView);
    }


}
