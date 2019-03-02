package com.lanshifu.demo_module.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * scrollview 嵌套 RecyclerView，内部拦截法
 */
public class InnerRecyclerView extends RecyclerView {



    public InnerRecyclerView(Context context) {
        super(context);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float lastY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            getParent().getParent().requestDisallowInterceptTouchEvent(true);
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){

            //向上滑动,且RecyclerView到底了
            if (ev.getY() < lastY){
                if (!canScrollVertically(1)){
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }

            //向下滑动，且RecyclerView到顶部了
            }else if(ev.getY() > lastY){
                if (!canScrollVertically(-1)){
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
        }

        lastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

}
