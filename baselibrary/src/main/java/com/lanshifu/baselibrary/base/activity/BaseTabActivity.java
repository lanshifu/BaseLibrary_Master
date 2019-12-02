package com.lanshifu.baselibrary.base.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.log.LogUtil;

/**
 * Created by lanxiaobin on 2017/8/1.
 */

public  abstract class BaseTabActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    protected ViewPager mViewPager;
    protected TabLayout mTabLayout;


    @Override
    protected void doAfterSetContentView() {
        super.doAfterSetContentView();
        mTabLayout = (TabLayout) findViewById(R.id.comm_tab_layout);
        mTabLayout.setTabTextColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mTabLayout.setSelectedTabIndicatorColor(R.color.green);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.base_tab_activity;
    }


    protected ViewPager getViewPager(){
        return mViewPager;
    }

    public TabLayout getTabLayout(){
        return mTabLayout;
    }

    /**
     * 使用内置的ViewPager进行初始化, 此时不必给getContentView返回布局资源id
     *
     * @param titles
     * @param fragments
     */
    protected void setupTabLayout(String[] titles, Fragment[] fragments) {

        if (titles.length != fragments.length){
            LogUtil.e("titles 和 fragments 大小不一致");
            throw new IllegalArgumentException("titles 和 fragments 大小不一致");
        }
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new InnerVPAdapter(getSupportFragmentManager(), titles, fragments));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public static class InnerVPAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments;
        private String[] mTitles;

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        public InnerVPAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
