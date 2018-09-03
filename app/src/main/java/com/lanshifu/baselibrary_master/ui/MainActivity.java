package com.lanshifu.baselibrary_master.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary_master.DefaultMvpFragment;
import com.lanshifu.baselibrary_master.R;
import com.lanshifu.baselibrary.RouterHub;

import butterknife.BindView;

public class MainActivity extends BaseTitleBarActivity {


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
    protected int getLayoutId() {
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

                case R.id.nav_about:
                    showFragment(3);
                    break;

                default:
                    break;
            }
        } else {
            showFragment(0);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        showFragment(0);
                        break;

                    case R.id.nav_video:
                        showFragment(1);
                        break;

                    case R.id.nav_picture:
                        showFragment(2);

                    case R.id.nav_about:
                        showFragment(3);
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
            }
        }

        //图片组件Fragment
        if (mPictureFragment == null) {
            mPictureFragment = UIUtil.navigationFragment(RouterHub.PICTURE_MAIN_FRAGMENT);
            if (mPictureFragment == null) {
                LogHelper.e("图片组件加载失败");
                mPictureFragment = new DefaultMvpFragment();
            }
        }
        if (mAboutFragment == null) {
            mAboutFragment = UIUtil.navigationFragment(RouterHub.WANDROID_MAIN_FRAGMENT);
            if (mAboutFragment == null) {
                LogHelper.e("玩安卓组件加载失败");
                mAboutFragment = new DefaultMvpFragment();
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
        super.onSaveInstanceState(outState);
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
        if (mHomeFragment != null){
            mHomeFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
