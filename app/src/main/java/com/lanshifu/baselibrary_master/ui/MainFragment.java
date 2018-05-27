package com.lanshifu.baselibrary_master.ui;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lanshifu.baselibrary.base.BaseFragment;
import com.lanshifu.baselibrary.utils.SharedPreUtils;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.theme.ColorUiUtil;
import com.lanshifu.baselibrary.widget.theme.Theme;
import com.lanshifu.baselibrary_master.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\5\5 0005.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.btn_switch_theme)
    Button btnSwitchTheme;
    @BindView(R.id.btn_2)
    Button btn2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_switch_theme, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_theme:
                changeTheme();
                break;

            case R.id.btn_2:
                startActivity(SecondActivity.class);
                break;
        }
    }

    private void changeTheme(){
        ToastUtil.showShortToast("主题更换");

        Theme currentTheme = SharedPreUtils.getInstance().getCurrentTheme();
        if (currentTheme == Theme.Blue){
            getActivity().setTheme(R.style.BlueGreyTheme);
            SharedPreUtils.getInstance().setCurrentTheme(Theme.BlueGrey);
        }else {
            getActivity().setTheme(R.style.BlueTheme);
            SharedPreUtils.getInstance().setCurrentTheme(Theme.Blue);
        }


        final View rootView = getActivity().getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);
        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getActivity().getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getActivity().getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }
}
