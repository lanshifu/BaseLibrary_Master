package com.lanshifu.demo_module.widget;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ScrollView嵌套listveiw 滑动冲突内部拦截法
 */
public class InnerListScrollView extends ScrollView {


    public InnerListScrollView(Context context) {
        super(context);
    }

    public InnerListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /** 内部拦截法需要父布局不拦截 ACTION_DOWN ,否则所有事件都给父布局了*/
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            //手动调用onTouchEvent
            onTouchEvent(ev);
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
