package com.lanshifu.demo_module.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.demo_module.R;

public class DemoSignCheckActivity extends BaseTitleBarActivity {

    /**
     * 签名值，可以由服务下发
     */
    private String mSign = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3138303430373132333831325a170d3438303333303132333831325a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100b17c182c0a3493e19daa18950ba77732b23394ed47f146a5a1e05706fe9625cb5da95e1c00cca11022f4618f740f17790bb13cd56dfe8b92159208b74ff80ab2baff363754051ecec20210731287fcc26b6096ab5ce0aff8e5c4f281863250cfd734db1f63ef25fb1861c3f6736bbbac945cda2b21a871871125b0323b0507610203010001300d06092a864886f70d01010505000381810037fd581ef26f0137bf8da6a17ff5bb717c2fd7eef534fa7eca11b1fecbcdc1668c994fae4c420ecf0ac5ad6998a9c7c29c47f668542538b793dc5d035b5bf6abab076f83bb724695efe5e60ff31817397ac248017edee24ed2c83a6ca57fabbd1df8520ee35d7f928b18e2f21a2589dc264798a84791fe29598435b51706c7b8";

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("签名验证");
        if (!isOwnApp()) {
            //  签名不对，kill
            showShortToast("签名不对");

        } else {
            showShortToast("签名正确");
        }
    }

    private String getSignature() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

            Signature[] signatures = packageInfo.signatures;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < signatures.length; i++) {
                sb.append(signatures[i].toCharsString());
            }
            return sb.toString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 签名验证
     *
     * @return
     */
    private boolean isOwnApp() {
        String signature = getSignature();
        LogHelper.d("签名：" + signature);
        return mSign.equals(signature);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }
}
