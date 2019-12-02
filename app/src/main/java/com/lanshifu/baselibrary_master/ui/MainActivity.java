package com.lanshifu.baselibrary_master.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary_master.DefaultMvpFragment;
import com.lanshifu.baselibrary_master.R;
import com.lanshifu.baselibrary.RouterHub;

import java.lang.reflect.Field;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID = "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";

    private static final String KEY_HOME_FRAGMENT = "key_home_fragment";
    private static final String KEY_VIDEO_FRAGMENT = "key_video_fragment";
    private static final String KEY_PICTURE_FRAGMENT = "key_picture_fragment";
    private static final String KEY_ABOUT_FRAGMENT = "key_about_fragment";

    private Fragment mHomeFragment;
    private Fragment mVideoFragment;
    private Fragment mPictureFragment;
    private Fragment mAboutFragment;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initFragments(savedInstanceState);

        if (savedInstanceState != null) {
            int selectId = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID);
            switch (selectId) {
                case R.id.nav_home:
                    showFragment(0);
                    break;

                case R.id.nav_video:
                    showFragment(1);
                    break;

                case R.id.nav_picture:
                    showFragment(2);
                    break;

                case R.id.nav_about:
                    showFragment(3);
                    break;

                default:
                    break;
            }
        } else {
            showFragment(0);
        }

        disableShiftMode(bottomNavigationView);
        showUnreadIcon(0,1);
        showUnreadIcon(1,2);
        showUnreadIcon(2,3);
        showUnreadIcon(3,4);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        showFragment(0);
                        showUnreadIcon(0, 0);
                        break;

                    case R.id.nav_video:
                        showFragment(1);
                        showUnreadIcon(1, 0);
                        break;

                    case R.id.nav_picture:
                        showFragment(2);
                        showUnreadIcon(2, 0);
                        break;

                    case R.id.nav_about:
                        showFragment(3);
                        showUnreadIcon(3, 0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mHomeFragment = fragmentManager.getFragment(savedInstanceState, KEY_HOME_FRAGMENT);
            mVideoFragment = fragmentManager.getFragment(savedInstanceState, KEY_VIDEO_FRAGMENT);
            mPictureFragment = fragmentManager.getFragment(savedInstanceState, KEY_PICTURE_FRAGMENT);
            mAboutFragment = fragmentManager.getFragment(savedInstanceState, KEY_ABOUT_FRAGMENT);
        }
        if (mHomeFragment == null) {
            mHomeFragment = new MainMvpFragment();
        }
        //视频组件Fragment
        if (mVideoFragment == null) {
            mVideoFragment = UIUtil.navigationFragment(RouterHub.VIDEO_MAIN_FRAGMENT);
            if (mVideoFragment == null) {
                mVideoFragment = new DefaultMvpFragment();
                ((DefaultMvpFragment) mVideoFragment).setContextText("视频组件加载失败");
            }
        }

        //图片组件Fragment
        if (mPictureFragment == null) {
            mPictureFragment = UIUtil.navigationFragment(RouterHub.PICTURE_MAIN_FRAGMENT);
            if (mPictureFragment == null) {
                LogUtil.e("图片组件加载失败");
                mPictureFragment = new DefaultMvpFragment();
                ((DefaultMvpFragment) mPictureFragment).setContextText("图片组件加载失败");
            }
        }
        if (mAboutFragment == null) {
            mAboutFragment = UIUtil.navigationFragment(RouterHub.WANDROID_MAIN_FRAGMENT);
            if (mAboutFragment == null) {
                LogUtil.e("玩安卓组件加载失败");
                mAboutFragment = new DefaultMvpFragment();
                ((DefaultMvpFragment) mAboutFragment).setContextText("玩安卓组件加载失败");
            }
        }

        if (!mHomeFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mHomeFragment, KEY_HOME_FRAGMENT)
                    .commit();
        }
        if (!mVideoFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mVideoFragment, KEY_VIDEO_FRAGMENT)
                    .commit();
        }
        if (!mPictureFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mPictureFragment, KEY_PICTURE_FRAGMENT)
                    .commit();
        }
        if (!mAboutFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mAboutFragment, KEY_ABOUT_FRAGMENT)
                    .commit();
        }
    }

    private void showFragment(int index) {
        LogUtil.d("showFragment:" + index);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (index) {
            case 0:
                fragmentManager.beginTransaction()
                        .show(mHomeFragment)
                        .hide(mVideoFragment)
                        .hide(mPictureFragment)
                        .hide(mAboutFragment)
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .hide(mHomeFragment)
                        .show(mVideoFragment)
                        .hide(mPictureFragment)
                        .hide(mAboutFragment)
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .hide(mHomeFragment)
                        .hide(mVideoFragment)
                        .show(mPictureFragment)
                        .hide(mAboutFragment)
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .hide(mHomeFragment)
                        .hide(mVideoFragment)
                        .hide(mPictureFragment)
                        .show(mAboutFragment)
                        .commit();
                break;
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //将这一行注释掉，阻止activity保存fragment的状态,解决Fragment穿透重叠现象
//        super.onSaveInstanceState(outState);

        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, bottomNavigationView.getSelectedItemId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mHomeFragment.isAdded()) {
            fragmentManager.putFragment(outState, KEY_HOME_FRAGMENT, mHomeFragment);
        }
        if (mVideoFragment.isAdded()) {
            fragmentManager.putFragment(outState, KEY_VIDEO_FRAGMENT, mVideoFragment);
        }
        if (mPictureFragment.isAdded()) {
            fragmentManager.putFragment(outState, KEY_PICTURE_FRAGMENT, mPictureFragment);
        }
        if (mAboutFragment.isAdded()) {
            fragmentManager.putFragment(outState, KEY_ABOUT_FRAGMENT, mPictureFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHomeFragment != null) {
            mHomeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 通过反射将点击变大的动画关闭，BottomNavigationMenuView 的 私有成员变量 mShiftingMode
     *
     * @param navigationView
     */
    private void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *未读角标
     * todo 需要将textView 保存起来，更新
     * @param index
     */
    private void showUnreadIcon(int index,int count) {
        LogUtil.d("showUnreadIcon index =" + index + ",count="+count);
        //如果

        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，
        if (menuView == null){
            LogUtil.e("menuView == null");
            return;
        }
        View tab = menuView.getChildAt(index);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.bottom_menu_badge, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        TextView textView = badge.findViewById(R.id.tv_msg_count);
        if (count >0){
            textView.setVisibility(View.VISIBLE);
            textView.setText(count +"");
        }else {
            textView.setVisibility(View.GONE);
        }
    }

}
