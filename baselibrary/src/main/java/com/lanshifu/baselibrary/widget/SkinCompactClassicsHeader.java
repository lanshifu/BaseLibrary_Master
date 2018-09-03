package com.lanshifu.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.lanshifu.baselibrary.R;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * 支持换肤的header
 */
public class SkinCompactClassicsHeader extends ClassicsHeader implements SkinCompatSupportable {

    private int mAccentColor = 0;
    private int mPrimaryColor = 0;

    public SkinCompactClassicsHeader(Context context) {
        this(context, null);
    }

    public SkinCompactClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompactClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAccentColor = SkinCompatResources.getInstance().getColor(R.color.refresh_header_textcolor);
        mPrimaryColor = SkinCompatResources.getInstance().getColor(R.color.refresh_header_bg);
       //手动设置颜色
        applyColor();
    }

    //这个方法是设置header里面字体颜色
    @Override
    public ClassicsHeader setAccentColor(int accentColor) {
        return super.setAccentColor(accentColor);
    }

    //header背景，以此类推
    @Override
    public ClassicsHeader setPrimaryColor(int primaryColor) {
        return super.setPrimaryColor(primaryColor);
    }

    private void applyColor() {
        setAccentColor(mAccentColor);
        setPrimaryColor(mPrimaryColor);
    }

    @Override
    public void applySkin() {
        applyColor();
    }
}
