package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.widget.SettingItemView;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.widget.DemoRelativeLayout;
import com.lanshifu.demo_module.widget.DemoTextView;

import butterknife.BindView;

/**
 * 触摸事件分发机制
 * https://mp.weixin.qq.com/s/Rt2EV_hZ_CysUJ-fisj3ug
 */
public class DemoTouchEventActivity extends BaseTitleBarActivity {
    @BindView(R2.id.tv_child)
    DemoTextView tvChild;
    @BindView(R2.id.rl_root)
    DemoRelativeLayout rlRoot;
    @BindView(R2.id.ll_switch_root)
    LinearLayout llSettingRoot;
    private SettingItemView mFirstItem;
    private SettingItemView mSecondItem;
    private SettingItemView mThirdItem;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_touch_event_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initListener();
        initSwitch();


    }

    private void initListener() {

        tvChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("child 点击");
                LogHelper.d("child onClick");
            }
        });
        tvChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showShortToast("child onTouch");
                LogHelper.d("child onTouch");
                //返回true 就不会走onClick
                return true;
            }
        });


        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("ViewGroup 点击");
                LogHelper.d("ViewGroup onClick");
            }
        });
        rlRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showShortToast("ViewGroup onTouch");
                LogHelper.d("ViewGroup onTouch");
                return false;
            }
        });
    }

    private void initSwitch() {
        //选择
        mFirstItem = new SettingItemView(this)
                .initCheckBox("ViewGroup  dispatchTouchEvent 返回", true)
                .OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        showShortToast("dispatchTouchEvent:" + isChecked);
                        rlRoot.setDispatchTouchEvent(isChecked);
                    }
                })
        .setCheck(false);

        mSecondItem = new SettingItemView(this)
                 .initCheckBox("ViewGroup  onInterceptTouchEvent 返回", true)
                 .OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         showShortToast("onInterceptTouchEvent:" + isChecked);
                         rlRoot.setOnInterceptTouchEvent(isChecked);
                     }
                 })
                .setCheck(false);

        mThirdItem = new SettingItemView(this)
                 .initCheckBox("ViewGroup  onTouchEvent 返回", true)
                 .OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         showShortToast("onTouchEvent :" + isChecked);
                         rlRoot.setOnTouchEvent(isChecked);
                     }
                 })
                .setCheck(false);

        llSettingRoot.addView(mFirstItem);
        llSettingRoot.addView(mSecondItem);
        llSettingRoot.addView(mThirdItem);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogHelper.d("Activity->dispatchTouchEvent:" + getEventName(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d("Activity->onTouchEvent:" + getEventName(event));
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
