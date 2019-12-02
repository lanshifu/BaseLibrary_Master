package com.lanshifu.baselibrary.base.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.swipeback.SlideBackView;
import com.lanshifu.baselibrary.utils.DensityUtil;
import com.lanshifu.baselibrary.utils.SystemUtil;

/**
 * 手 势 右 滑 关 闭
 */
public abstract class BaseSlideBackActivity extends BaseActivity {


    private static final String TAG = "BaseSlideBackActivity";
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mShouldFinishPix = 0;
    //手指按下左边距离左边多远，触发滑动
    private static final int CANSLIDE_LENGTH = 16;
    //判断是否从左边缘划过来
    private boolean mIsEdge = false;
    private float mDownX;
    private float mMoveX;
    private int offset = 0;
    private SlideBackView mSlideBackView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScreenWidth = SystemUtil.getScreenWidth(this);
        mScreenHeight = SystemUtil.getScreenHeigh(this);
        mShouldFinishPix = mScreenWidth / 3;

        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        mSlideBackView = (SlideBackView) getLayoutInflater().inflate(R.layout.base_swipe_back_view, null);
        mSlideBackView.setScreenWidth(mScreenWidth);
        addListener(mSlideBackView);
        decorView.addView(mSlideBackView);
    }


    private void addListener(final SlideBackView slideBackView) {

        slideBackView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = motionEvent.getRawX();
                        Log.d(TAG, "mDownX: " + mDownX);

                        //判断点击范围与设置的滑出区域是否符合
                        if (mDownX > DensityUtil.dp2px(CANSLIDE_LENGTH)) {
                            return false;
                        }

                        //手指落在左边8dp距离
                        mIsEdge = true;
                        slideBackView.updateControlPoint(0, offset);
                        setBackViewY(slideBackView, (int) (motionEvent.getRawY()));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mIsEdge) {
                            mMoveX = motionEvent.getRawX() - mDownX;
                            if (Math.abs(mMoveX) <= mShouldFinishPix) {
                                slideBackView.updateControlPoint(Math.abs(mMoveX) / 2, offset);
                            }
                            setBackViewY(slideBackView, (int) (motionEvent.getRawY()));
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //从左边缘划过来，并且最后在屏幕的三分之一外
                        if (mIsEdge) {
                            if (mMoveX >= mShouldFinishPix) {
                                slideBackSuccess();
                            }
                        }
                        mIsEdge = false;
                        slideBackView.updateControlPoint(0, offset);
                        break;
                }
                if (mIsEdge) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    public void setBackViewY(View view, int y) {
        //判断是否超出了边界
        int topMargin = y - DensityUtil.dp2px(SlideBackView.height) / 2;
        if (topMargin < 0 || y > mScreenHeight - DensityUtil.dp2px(SlideBackView.height) / 2) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getLayoutParams());
        layoutParams.topMargin = topMargin;
        view.setLayoutParams(layoutParams);
    }

    protected void slideBackSuccess() {
        LogUtil.d("slideBackSuccess");
        finish();
    }
}
