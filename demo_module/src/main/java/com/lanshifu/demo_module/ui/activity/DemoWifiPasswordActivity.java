package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        recyclerView.autoRefresh();
        recyclerView.setEnableLoadMore(false);
        recyclerView.setEnableRefresh(false);

        getWiFiPw();
    }


    public class WifiInfo {
        public String Ssid = "";
        public String Password = "";

    }

    private void getWiFiPw() {
        Observable.create(new ObservableOnSubscribe<List<WifiInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WifiInfo>> emitter) throws Exception {
                List<WifiInfo> wifiInfos = ReadWifiFile();
                emitter.onNext(wifiInfos);
                emitter.onComplete();
            }
        }).compose(RxScheduler.io_main())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<List<WifiInfo>>() {
                    @Override
                    public void _onNext(List<WifiInfo> wifiInfos) {
                        adapter.addData(wifiInfos);
                    }

                    @Override
                    public void _onError(String e) {
                        ToastUtil.showShortToast(e);
                    }
                });


    }



    public List<WifiInfo> ReadWifiFile() throws Exception {
        List<WifiInfo> wifiInfos=new ArrayList<WifiInfo>();

        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/*.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                throw e;
            }
        }


        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString() );
        while (networkMatcher.find() ) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find() ) {
                WifiInfo wifiInfo=new WifiInfo();
                wifiInfo.Ssid=ssidMatcher.group(1);
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find() ) {
                    wifiInfo.Password=pskMatcher.group(1);
                } else {
                    wifiInfo.Password="无密码";
                }
                wifiInfos.add(wifiInfo);
            }

        }

        return wifiInfos;
    }

}
