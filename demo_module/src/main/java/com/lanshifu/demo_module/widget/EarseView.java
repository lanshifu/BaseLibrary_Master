package com.lanshifu.demo_module.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lanshifu on 2018/10/26.
 */

public class EarseView extends View{
    public EarseView(Context context) {
        super(context);
    }

    public EarseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EarseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
}
