package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.bean.WifiInfo;
import com.lanshifu.demo_module.util.WifiPasswordUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DemoWifiPasswordActivity extends BaseTitleBarActivity {
    @BindView(R2.id.recyclerView)
    CommRecyclerView recyclerView;

    private BaseQuickAdapter<WifiInfo,BaseViewHolder> adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("wifi密码查看");

        adapter = new BaseQuickAdapter<WifiInfo,BaseViewHolder>(R.layout.demo_list_item) {
                    @Override
                    protected void convert(BaseViewHolder baseViewHolder, WifiInfo wifiInfo) {
                        baseViewHolder.setText(R.id.tv_title, wifiInfo.Ssid);
                        baseViewHolder.setText(R.id.tv_content, wifiInfo.Password);
                    }
                };

        recyclerView.setAdapter(adapter);
        recyclerView.setEnableLoadMore(false);
        recyclerView.setEnableRefresh(false);

        getWiFiPw();
    }


    private void getWiFiPw() {
        startProgressDialog();
        Observable.create(new ObservableOnSubscribe<List<WifiInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WifiInfo>> emitter) throws Exception {
                List<WifiInfo> wifiInfos = WifiPasswordUtil.readWifiPassworld();
                emitter.onNext(wifiInfos);
                emitter.onComplete();
            }
        }).compose(RxScheduler.io_main())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<List<WifiInfo>>() {
                    @Override
                    public void _onNext(List<WifiInfo> wifiInfos) {
//                        ToastUtil.showShortToast(wifiInfos.size() +"");
                        adapter.addData(wifiInfos);
                        stopProgressDialog();
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast(e);
                        stopProgressDialog();
                    }
                });


    }


}
