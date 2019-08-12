package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.demo_module.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanshifu on 2018/10/18.
 */

public class DemoBehavior2Activity extends BaseActivity {


    @Override
    protected int setContentViewId() {
        return R.layout.demo_behavior2_activty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.demo_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String data) {
                baseViewHolder.setText(R.id.tv_title, data);
            }
        };
        recyclerView.setAdapter(adapter);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("数据"+i);
        }

        adapter.addData(datas);

    }
}
