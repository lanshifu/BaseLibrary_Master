package com.lanshifu.baselibrary_master.ui;

import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.baselibrary_master.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\5\19 0019.
 */

public class SecondActivity extends BaseTitleBarActivity {
    @BindView(R.id.recyclerView)
    CommRecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        setTitleText("第二个activity");
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_textview) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String data) {
                baseViewHolder.setText(R.id.textview, data);
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
        recyclerView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }
        });

        adapter.addData("数据1");
        adapter.addData("数据2");
        adapter.addData("数据3");
    }

    @Override
    protected int getTBMenusId() {
        return R.menu.menu_second_activity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        showShortToast(item.getTitle().toString());

        return super.onOptionsItemSelected(item);
    }


    /**
     * 通过反射，设置menu显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

}
