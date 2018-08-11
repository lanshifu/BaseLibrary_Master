package com.lanshifu.picture_module.ui.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.lanshifu.baselibrary.base.BaseActivity;
import com.lanshifu.picture_module.R;
import com.lanshifu.picture_module.R2;

import butterknife.BindView;

public class PictureDetailActivity extends BaseActivity {
    @BindView(R2.id.photo_view)
    PhotoView photoView;

    @Override
    protected int setContentViewId() {
        return R.layout.picture_detail_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .into(photoView);

    }


}
