package com.lanshifu.baselibrary_master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanshifu.baselibrary.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\4\22 0022.
 */

public class DefaultMvpFragment extends BaseFragment {
    @BindView(R2.id.tv_content)
    TextView mTvContent;
    Unbinder unbinder;
    String text = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_default;
    }

    @Override
    protected void initView() {
        mTvContent.setText(text);
    }

    public void setContextText(String text) {
        this.text = text;
    }

}
