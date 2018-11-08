package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;

import butterknife.BindView;

/**
 * 通过popupwindow，背景半透明，
 * 在某个控件的位置设置为透明，达到高亮的效果
 * <p>
 * Created by lanshifu on 2018/10/26.
 */

public class DemoGuideActivity extends BaseTitleBarActivity {
    @BindView(R2.id.btn_1)
    Button mBtn1;
    @BindView(R2.id.btn_2)
    Button mBtn2;

    PopupWindow mPopWindow;

    Runnable showPopupWindow1 = new Runnable() {
        @Override
        public void run() {
            View popView = LayoutInflater.from(mContext).inflate(R.layout.demo_guide_1, null, false);
            popView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dismiss();
                    mBtn2.postDelayed(showPopupWindow2, 1000);
                }
            });

            mPopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
            mPopWindow.setOutsideTouchable(false);
            //显示在哪个位置好像都没关系，因为  PopupWindow 是 MATCH_PARENT 的
            mPopWindow.showAsDropDown(mBtn2);
        }
    };

    Runnable showPopupWindow2 = new Runnable() {
        @Override
        public void run() {
            View popView = LayoutInflater.from(mContext).inflate(R.layout.demo_guide_2, null, false);
            LinearLayout rootView = (LinearLayout) popView.findViewById(R.id.ll_root);
            popView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dismiss();

                }
            });
            mPopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.showAsDropDown(mBtn2);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.demo_guide_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("引导层");

        mBtn1.postDelayed(showPopupWindow1, 1000);

    }

}
