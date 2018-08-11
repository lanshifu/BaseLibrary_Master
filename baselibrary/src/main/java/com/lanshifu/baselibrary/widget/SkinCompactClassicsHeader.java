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

    public SkinCompactClassicsHeader(Context context) {
        this(context, null);
    }

    public SkinCompactClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompactClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader, defStyleAttr, 0);
        if (ta.hasValue(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlAccentColor)) {
            mAccentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlAccentColor, 0);
        }
        ta.recycle();
//        //手动设置颜色
//        mAccentColor = context.getResources().getColor(R.color.colorAccent);
        applyAccentColor();
    }


    @Override
    public ClassicsHeader setAccentColor(int accentColor) {
        return super.setAccentColor(accentColor);
    }

    private void applyAccentColor() {
        mAccentColor = SkinCompatHelper.checkResourceId(mAccentColor);
        if (mAccentColor != INVALID_ID) {
            int color = SkinCompatResources.getColor(getContext(), mAccentColor);
            setAccentColor(color);
        }
    }

    @Override
    public void applySkin() {
        applyAccentColor();
    }
}
