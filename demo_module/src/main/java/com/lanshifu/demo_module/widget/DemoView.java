package com.lanshifu.demo_module.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lanshifu.baselibrary.log.LogHelper;

public class DemoView extends View {

    private boolean dispatchTouchEvent = false;
    private boolean onInterceptTouchEvent = false;
    private boolean onTouchEvent = false;

    public void setDispatchTouchEvent(boolean dispatchTouchEvent) {
        this.dispatchTouchEvent = dispatchTouchEvent;
    }

    public void setOnInterceptTouchEvent(boolean onInterceptTouchEvent) {
        this.onInterceptTouchEvent = onInterceptTouchEvent;
    }

    public void setOnTouchEvent(boolean onTouchEvent) {
        this.onTouchEvent = onTouchEvent;
    }

    public DemoView(Context context) {
        super(context);
        init();
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogHelper.d("View onClick");
//            }
//        });
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogHelper.d("View onTouch");
//                return false;
//            }
//        });
//        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogHelper.d("View->dispatchTouchEvent:" + getEventName(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d("View->onTouchEvent:" + getEventName(event));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
//        return true;
    }

    private String getEventName(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else if (event.getAction() ==MotionEvent.ACTION_CANCEL) {
            return "ACTION_CANCEL";
        } else {
            return event.getAction() + "";
        }
    }
}
