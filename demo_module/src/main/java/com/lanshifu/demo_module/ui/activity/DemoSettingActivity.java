package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.widget.SettingItemView;
import com.lanshifu.demo_module.R;

import butterknife.BindView;

public class DemoSettingActivity extends BaseTitleBarActivity
        implements SettingItemView.OnRootClickListener, SettingItemView.OnArrowClickListener {
    @BindView(R.id.ll_setting_root)
    LinearLayout llSettingRoot;
    private SettingItemView mFirstItem;
    private SettingItemView mSecondItem;
    private SettingItemView mThirdItem;
    private SettingItemView mFourItem;
    private SettingItemView mFireItem;
    private SettingItemView mSixItem;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_setting_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("设置");

        //icon + 文字 + 箭头
        mFirstItem = new SettingItemView(this)
                .initMine(R.mipmap.ic_launcher, "第一行", "", true)
                .setOnRootClickListener(this, 1);

        //icon + 文字 + 文字 + 箭头
        mSecondItem = new SettingItemView(this)
                .initMine(R.mipmap.ic_launcher, "第二行", "第二行", true)
                .setOnRootClickListener(this, 2)
                .showDivider(false, true);

        //icon + 文字 + 输入框
        mThirdItem = new SettingItemView(this)
                .initItemWidthEdit(R.mipmap.ic_launcher, "第三行", "这是一个输入框")
                .setOnRootClickListener(this, 3)
                .showDivider(false, true);

        //文字 + 箭头
        mFourItem = new SettingItemView(this)
                .init("第4行")
                .showArrow(true)
                .setRightText("哈哈哈")
                .setOnRootClickListener(this, 4)
                .showDivider(false, true);

        //选择
        mFireItem = new SettingItemView(this)
                .initCheckBox("第5行", true)
                .setOnRootClickListener(this, 5)
                .OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        showShortToast("" + isChecked);
                    }
                });

        mSixItem = new SettingItemView(this)
                .initCheckBox("第6行", true)
                .setOnRootClickListener(this, 6)
                .OnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        showShortToast("" + isChecked);
                        buttonView.setChecked(!isChecked);
                    }
                });

        llSettingRoot.addView(mFirstItem);
        llSettingRoot.addView(mSecondItem);
        llSettingRoot.addView(mThirdItem);
        llSettingRoot.addView(mFourItem);
        llSettingRoot.addView(mFireItem);
        llSettingRoot.addView(mSixItem);
    }

    @Override
    public void onRootClick(View view) {
        int position = (int) view.getTag();
        showShortToast("点击了第" + position + "行");
    }

    @Override
    public void onArrowClick(View view) {
        int position = (int) view.getTag();
        showShortToast("点击了第" + position + "行");
    }
}
