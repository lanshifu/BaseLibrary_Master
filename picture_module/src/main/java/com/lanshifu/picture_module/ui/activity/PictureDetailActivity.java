package com.lanshifu.picture_module.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.picture_module.R;
import com.lanshifu.picture_module.R2;

import butterknife.BindView;

public class PictureDetailActivity extends BaseActivity {
    @BindView(R2.id.photo_view)
    PhotoView photoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.picture_detail_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(R.id.photo_view);
            getWindow().setEnterTransition(slide);
        }

        String url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .into(photoView);

    }


}
