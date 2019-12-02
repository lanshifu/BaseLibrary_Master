package com.lanshifu.baselibrary.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.lanshifu.baselibrary.log.LogUtil;

/**
 * 顶部toast布局，主要处理消失时候取消handler消息
 * Created by lanshifu on 2017/9/16.
 */

public class TopToastContentView extends LinearLayout {

    public static final int WHAT = 0;
    private int mWidth = 1080;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (onHandlerCallBackListner != null) {
                    onHandlerCallBackListner.handlerMsg();
                }
            }
        }
    };

    public TopToastContentView(Context context) {
        this(context,null);
    }

    public TopToastContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopToastContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mWidth = getMeasuredWidth();
                LogUtil.d("mWidth = " + mWidth);
            }
        });
        setTouchListener();
    }

    private int mStartX;
    private int mEndX;
    private void setTouchListener() {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        if (needIntercept()) {
                            int moveX = (int) (event.getRawX() - mStartX);

                            setX(moveX);
                            return true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            //退出动画
                            int moveX = mEndX - mStartX;
                            LogUtil.d("moveX "+moveX);

                            if (moveX > (getMeasuredWidth() /3)){
                                animOut();
                            }else {
                                //弹回动画
                                animIn();
                            }
                            return true;
                        }
                        break;

                }

                return false;
            }
        };
        setOnTouchListener(onTouchListener);
    }

    //向右滑动拦截
    private boolean needIntercept() {
        return (mEndX - mStartX) > 30 ;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(WHAT);
    }

    public void setOnHandlerCallBackListner(OnHandlerCallBackListner onHandlerCallBackListner) {
        this.onHandlerCallBackListner = onHandlerCallBackListner;
    }

    public OnHandlerCallBackListner onHandlerCallBackListner;

    public interface OnHandlerCallBackListner {
        void handlerMsg();
    }

    public interface OnRemoveListner {
        void remove();
    }

    private void animIn(){
        LogUtil.d("animIn getX() " + getX());
//        TranslateAnimation animationIn = new TranslateAnimation(getX(), 0, getY(), getY());
//        animationIn.setDuration(1000);
//        animationIn.setFillAfter(true);
//        startAnimation(animationIn);

        setX(0);

    }

    private void animOut(){
        ObjectAnimator animator = new ObjectAnimator();
        animator.setIntValues();

        //view动画，没有改变位置
        TranslateAnimation animationOut = new TranslateAnimation(0, getMeasuredWidth(), getY(), getY());
        animationOut.setDuration(1000);
        animationOut.setFillAfter(true);
        startAnimation(animationOut);

    }



}
