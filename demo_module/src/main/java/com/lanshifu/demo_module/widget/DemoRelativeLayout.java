package com.lanshifu.demo_module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.lanshifu.baselibrary.log.LogHelper;

public class DemoRelativeLayout extends RelativeLayout{

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

    public DemoRelativeLayout(Context context) {
        super(context);
    }

    public DemoRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogHelper.d("ViewGroup->dispatchTouchEvent:" + getEventName(ev));
//        return dispatchTouchEvent;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogHelper.d("ViewGroup->onInterceptTouchEvent:" + getEventName(ev));
//        return onInterceptTouchEvent;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d("ViewGroup->onTouchEvent:" + getEventName(event));
//        return onTouchEvent;
        return super.onTouchEvent(event);
    }




    private String getEventName(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else {
            return event.getAction() + "";
        }
    }
}
