package com.lanshifu.demo_module.behavior;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lanshifu.baselibrary.log.LogUtil;

/**
 *标题跟着随着列表往上滑动而出来和回去
 * Created by lanshifu on 2018/10/18.
 */

public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 使用该Behavior的View要监听哪个类型的View的状态变化。其中参数parant代表CoordinatorLayout，
     * child代表使用该Behavior的View，dependency代表要监听的View。这里要监听RecyclerView。
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }


    /**
     * 当被监听的View状态变化时会调用该方法，参数和上一个方法一致。所以我们重写该方法，当RecyclerView的位置变化时，进而改变title的位置。
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            //列表顶部的位置减去标题textview底部的位置，就是滑动的最大距离
            deltaY = dependency.getY() - child.getHeight();
            LogUtil.d("deltaY = " + deltaY);
        }

        float dy = dependency.getY() - child.getHeight(); //  rv移动到tv上面去就是 负数，负数不setTranslationY（0）
        LogUtil.d("dy = " + dy);
        dy = dy < 0 ? 0 : dy;
//        float y = -(dy / deltaY) * child.getHeight();
        float y = -(dy / deltaY) * child.getHeight();   //- 表示手指滑动方向相反 移动
        //根据rv滑动，标题view上下移动
        child.setTranslationY(y);

        return true;
    }

}
