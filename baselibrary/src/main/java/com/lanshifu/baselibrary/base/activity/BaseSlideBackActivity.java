package com.lanshifu.baselibrary.base.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.swipeback.SlideBackView;
import com.lanshifu.baselibrary.utils.DensityUtil;
import com.lanshifu.baselibrary.utils.SystemUtil;

/**
 * 手 势 右 滑 关 闭
 */
public abstract class BaseSlideBackActivity extends BaseActivity {


    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    //
    private int mShouldFinishPix = 0;
    private static final int CANSLIDE_LENGTH = 16;

    //判断是否从左边缘划过来
    boolean mIsEdge = false;
    float x;
    float downX;

    int offset;
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
                downX = motionEvent.getRawX();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getRawX();

                        //判断点击范围与设置的滑出区域是否符合
                        if (downX > mScreenWidth / 2) {
                            //在右侧区域，直接return
                            return false;
                        } else {
                            offset = 0;
                        }


                        //手指落在左边16dp距离
                        if (x <= DensityUtil.dp2px(CANSLIDE_LENGTH)) {
                            mIsEdge = true;
                            slideBackView.updateControlPoint(Math.abs(x), offset);
                            setBackViewY(slideBackView, (int) (motionEvent.getRawY()));
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mIsEdge) {
                            float moveX = Math.abs(mScreenWidth * offset - x) - downX;
                            if (Math.abs(moveX) <= mShouldFinishPix) {
                                slideBackView.updateControlPoint(Math.abs(moveX) / 2, offset);
                            }
                            setBackViewY(slideBackView, (int) (motionEvent.getRawY()));
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //从左边缘划过来，并且最后在屏幕的三分之一外
                        if (mIsEdge) {
                            if (x >= mShouldFinishPix) {
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
        LogHelper.d("slideBackSuccess");
        finish();
    }
}
