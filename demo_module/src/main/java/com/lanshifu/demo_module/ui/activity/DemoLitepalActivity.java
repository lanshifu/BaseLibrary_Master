package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.db.DemoDb;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.tablemanager.callback.DatabaseListener;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by lanshifu on 2018/10/13.
 */

public class DemoLitepalActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.demo_litepal_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("litepal 3.0");

        LitePal.registerDatabaseListener(new DatabaseListener() {
            @Override
            public void onCreate() {
                LogUtil.d(" db onCreate");
            }

            @Override
            public void onUpgrade(int oldVersion, int newVersion) {
                LogUtil.d(" db onUpgrade,oldVersion =" + oldVersion + ",newVersion=" + newVersion);
            }
        });
    }


    @OnClick({R2.id.btn_db_change, R2.id.btn_insert, R2.id.btn_update, R2.id.btn_delete, R2.id.btn_find})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_db_change) {


        } else if (viewId == R.id.btn_insert) {
            new DemoDb("name", 30, 200000).saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    LogUtil.d("插入 " + success);
                    runOnUiThread(() -> {
                        showShortToast("插入 " + success);
                    });

                }
            });
        } else if (viewId == R.id.btn_find) {
            LitePal.findAllAsync(DemoDb.class)
                    .listen(new FindMultiCallback<DemoDb>() {
                        @Override
                        public void onFinish(List<DemoDb> list) {
                            LogUtil.d("查询数据总数 " + list.size());
                            runOnUiThread(() -> {
                                showShortToast("查询数据总数 " + list.size());
                            });
                        }
                    });
        }


    }
}
