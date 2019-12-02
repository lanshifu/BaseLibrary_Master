package com.lanshifu.demo_module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.lanshifu.baselibrary.log.LogUtil;

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
        LogUtil.d("ViewGroup->dispatchTouchEvent:" + getEventName(ev));
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d("ViewGroup->onInterceptTouchEvent:" + getEventName(ev));

        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("ViewGroup->onTouchEvent:" + getEventName(event));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                return true;
//                break;
            case MotionEvent.ACTION_UP:
                break;
        }
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
