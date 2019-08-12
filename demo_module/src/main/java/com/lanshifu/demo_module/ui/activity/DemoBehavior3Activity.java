package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanshifu on 2018/10/18.
 */

public class DemoBehavior3Activity extends BaseActivity {


    @Override
    protected int setContentViewId() {
        return R.layout.demo_behavior3_activty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        TextView tv_show_hide = findViewById(R.id.tv_show_hide);
        LinearLayout ll_bottom = findViewById(R.id.ll_bottom);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.demo_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String data) {
                baseViewHolder.setText(R.id.tv_title, data)
                .addOnClickListener(R.id.tv_title);
            }
        };
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_title){
                    ToastUtil.showShortToastInThread("点击了" + adapter.getData().get(position));
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("数据"+i);
        }

        adapter.addData(datas);


        //获取BottomSheetBehavior
        final BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(ll_bottom);
        //设置折叠时的高度
//        sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //监听BottomSheetBehavior 状态的变化
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //下滑的时候是否可以隐藏
        sheetBehavior.setHideable(true);

        tv_show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }
}
