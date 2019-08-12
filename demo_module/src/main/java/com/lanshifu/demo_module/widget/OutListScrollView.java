package com.lanshifu.demo_module.widget;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ScrollView嵌套listveiw 滑动冲突外部拦截法
 */
public class OutListScrollView extends ScrollView {

    public OutListScrollView(Context context) {
        super(context);
    }

    public OutListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private RecyclerView mRecyclerView;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (needIntercept(ev)){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }


    private int left;
    private int right;
    private int top;
    private int bottom;

    /**
     * 外部拦截的条件
     * @return
     */
    private boolean needIntercept(MotionEvent event) {
        if (mRecyclerView == null){
            return true;
        }

        /**判断RecyclerView到达顶部或者底部，就拦截*/
        // 测试view是否在点击范围内

        /**g etRawX是以屏幕左上角为坐标做预案，获取X坐标轴上的值。*/
        float x = event.getRawX();
        float y = event.getRawY();

        int[] locate = new int[2];
        mRecyclerView.getLocationOnScreen(locate);
        left = locate[0];
        right = left + mRecyclerView.getWidth();
        top = locate[1];
        bottom = top + mRecyclerView.getHeight();

        if (top > y || y > bottom) {
            return true;
        }
        return false;
    }


}
