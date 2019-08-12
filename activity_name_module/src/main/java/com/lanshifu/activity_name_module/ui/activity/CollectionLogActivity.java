package com.lanshifu.activity_name_module.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.activity_name_module.R;
import com.lanshifu.activity_name_module.db.CollectionDB;
import com.lanshifu.baselibrary.base.activity.BaseListTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lanshifu on 2018/11/2.
 */

public class CollectionLogActivity extends BaseListTitleBarActivity<BasePresenter<BaseView>,CollectionDB> {
    @Override
    protected BaseView bindView() {
        return null;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_collection_log;
    }

    @Override
    protected void convertData(BaseViewHolder baseViewHolder, CollectionDB data) {
        baseViewHolder.setText(R.id.tv_time,"日期："+getDateToString(data.time,"yyyy-MM-dd HH:mm:ss"))
                .setText(R.id.tv_count,"次数："+data.count+"");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("收集能量记录");
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setEnableLoadMore(false);
//        mRecyclerView.setEnableRefresh(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否删除这条记录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.delete(CollectionDB.class,mAdapter.getItem(position).id);
                        loadData();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadData();

    }

    private void loadData(){
        LitePal.findAllAsync(CollectionDB.class).listen(new FindMultiCallback<CollectionDB>() {
            @Override
            public void onFinish(List<CollectionDB> list) {
//                for (int i = 0; i < 20; i++) {
//                    CollectionDB db=  new CollectionDB(System.currentTimeMillis(),0);
//                    list.add(db);
//                }
                mAdapter.replaceData(list);
                mRecyclerView.finishRefresh();
            }
        });

    }


    @Override
    protected boolean isNeedStatusBarSkin() {
        return false;
    }

    /**
     * 时间戳转换成字符窜
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
