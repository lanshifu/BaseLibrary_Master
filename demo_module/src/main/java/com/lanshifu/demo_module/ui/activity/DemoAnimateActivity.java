package com.lanshifu.demo_module.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lanshifu on 2018/11/27.
 */

public class DemoAnimateActivity extends BaseTitleBarActivity {
    @BindView(R.id.btn_anim1)
    Button mBtnAnim1;
    @BindView(R.id.btn_anim2)
    Button mBtnAnim2;
    @BindView(R.id.btn_anim3)
    Button mBtnAnim3;
    @BindView(R.id.btn_anim4)
    Button mBtnAnim4;
    @BindView(R.id.btn_target)
    Button mBtnTarget;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_animate_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText("动画");

        mBtnTarget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }


    @OnClick({R.id.btn_anim1, R.id.btn_anim2, R.id.btn_anim3, R.id.btn_anim4, R.id.btn_target})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_anim1:
                anim1();
                break;
            case R.id.btn_anim2:
                anim2();
                break;
            case R.id.btn_anim3:
                anim3();
                break;
            case R.id.btn_anim4:
                anim4();
                break;
            case R.id.btn_target:
                ToastUtil.showShortToast("hello");
                break;
        }
    }

    private void anim1() {

        int animHeigh = getWindowManager().getDefaultDisplay().getHeight();//得到屏幕宽高
        int animWidth = getWindowManager().getDefaultDisplay().getWidth();
        ValueAnimator objectAnimator = ObjectAnimator.ofObject(new MyEvaluator(), new Point(0, 0),
                new Point(animWidth - 400, animHeigh - 400));//加载自定义的估值器,起始点坐标.
        objectAnimator.setDuration(5000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//设置帧动画 监听.
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                mBtnTarget.setX(point.x);//动态修改坐标
                mBtnTarget.setY(point.y);
            }
        });
        objectAnimator.start();

    }

    private void anim2() {
        int animHeigh = getWindowManager().getDefaultDisplay().getHeight();//得到屏幕宽高
        int animWidth = getWindowManager().getDefaultDisplay().getWidth();
        ValueAnimator objectAnimator = ObjectAnimator.ofObject(new MyEvaluator(), new Point(0, 0),
                new Point(animWidth - 400, animHeigh - 400));//加载自定义的估值器,起始点坐标.
        objectAnimator.setDuration(5000);
        objectAnimator.setInterpolator(new BounceInterpolator());//设置差值器效果
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//设置帧动画 监听.
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                mBtnTarget.setX(point.x);//动态修改坐标
                mBtnTarget.setY(point.y);
            }
        });
        objectAnimator.start();
    }

    private void anim3() {
        Animation alphaAnimation = new AlphaAnimation(0,1);
        // 步骤2：创建透明度动画的对象 & 设置动画效果

        alphaAnimation.setDuration(3000);
        Interpolator overshootInterpolator = new OvershootInterpolator();
        // 步骤3：创建对应的插值器类对象

        alphaAnimation.setInterpolator(overshootInterpolator);
        // 步骤4：给动画设置插值器

        mBtnTarget.startAnimation(alphaAnimation);
        // 步骤5：播放动画
    }

    private void anim4() {

    }

    public class MyEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            Point point = new Point();
            //y^2=x;(x>0) 就是抛物线~这里100是为了扩大像素的位移量.
            point.x = (int) (startValue.x + fraction * (endValue.x - startValue.x));
            point.y = (int) (startValue.y + (fraction * (endValue.y - startValue.y)) * fraction * fraction);

            return point;
        }
    }

}
