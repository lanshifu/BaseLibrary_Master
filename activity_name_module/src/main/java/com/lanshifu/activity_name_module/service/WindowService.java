package com.lanshifu.activity_name_module.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.lanshifu.activity_name_module.R;
import com.lanshifu.baselibrary.baserxjava.RxManager;

import java.util.Random;

import io.reactivex.functions.Consumer;

public class WindowService extends Service {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wmParams;
    private View mWindowView;
    private TextView mTvName;
    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;
    private RxManager mRxManager;
    private TextView mTvAppend;

    @Override
    public void onCreate() {
        super.onCreate();

        initWindowParams();
        initView();
        addWindowView();
        initClick();
        mRxManager = new RxManager();
        mRxManager.on("packagename", new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (mTvName != null) {
                    mTvName.setText(s);
                }
            }
        });

    }

    private void addWindowView() {
        mWindowManager.addView(mWindowView, wmParams);
    }

    private void initView() {

        mWindowView = LayoutInflater.from(this).inflate(R.layout.layout_windw, null);
        mTvName = (TextView) mWindowView.findViewById(R.id.percent);
        mTvAppend = (TextView) mWindowView.findViewById(R.id.tv_append);
        mTvAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTvName.getVisibility() == View.VISIBLE){
                    mTvName.setVisibility(View.GONE);
                    mTvAppend.setText("展开包名");
                }else {
                    mTvName.setVisibility(View.VISIBLE);
                    mTvAppend.setText("收起包名");
                }
            }
        });

    }

    private void initWindowParams() {
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
//        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParams.format = PixelFormat.TRANSLUCENT;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowView != null) {
            mWindowManager.removeView(mWindowView);
        }
        if (mRxManager != null) {
            mRxManager.clear();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void initClick() {

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        mStartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        mEndY = (int) event.getRawY();
                        if (needIntercept()) {
                            wmParams.x = (int) event.getRawX() - mWindowView.getMeasuredWidth() / 2;
                            wmParams.y = (int) event.getRawY() - mWindowView.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(mWindowView, wmParams);
                            return true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (needInterceptUp()) {
                            return true;
                        }
                        break;

                }

                return false;
            }
        };
        mWindowView.setOnTouchListener(onTouchListener);

        mWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int rgb = Color.rgb(random.nextInt(250), random.nextInt(250), random.nextInt(250));
                mTvName.setBackgroundColor(rgb);
            }
        });


    }


    private boolean needIntercept() {
        return Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30;
    }


    private boolean needInterceptUp() {
        return Math.abs(mStartX - mEndX) > 100 || Math.abs(mStartY - mEndY) > 100;
    }


}
