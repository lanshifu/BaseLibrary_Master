package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/** https://www.jianshu.com/p/e54db232df62 */
//{@link}
public class SnapHelperTestActivity extends BaseTitleBarActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.demo_snaphelper_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(manager);
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.demo_snaphelper_list_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String data) {

            }
        };

        mRecyclerView.setAdapter(adapter);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("数据："+i);
        }
        adapter.addData(datas);

        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
    }

}
