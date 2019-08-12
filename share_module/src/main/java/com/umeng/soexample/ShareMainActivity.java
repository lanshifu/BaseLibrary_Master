package com.umeng.soexample;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.OnClick;

/**
 * Created by lanshifu on 2018/10/6.
 */

public class ShareMainActivity extends BaseTitleBarActivity {
    private static final String TAG = "lxb-ShareMainActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.share_main_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("友盟分享登录");

        initPermission();



        //初始化sdk
        /**
         * 参数1:上下文，必须的参数，不能为空。

         参数2:【友盟+】 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。

         参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。

         参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机。

         参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(this,"561cae6ae0f55abd990035bf"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.setLogEnabled(true);

        /**
         * 微信开发者平台申请AppID，AppSecret
         链接：https://open.weixin.qq.com/

         这里用友盟提供的例子，还有签名
         */
        //微信，微信登录需要认证，所以采用友盟提供的，改包名
//        PlatformConfig.setWeixin("wxd8d5e30de42f6182", "eb40ace4835fde9bd8c37e6585133eef");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //QQ
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        //每次都拉起应用
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);


    }

    private void initPermission(){
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

    }


    @OnClick({R2.id.btn_wx_share, R2.id.btn_qq_share, R2.id.btn_wx_login, R2.id.btn_qq_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_wx_share) {
            wxShare();
        } else if (i == R.id.btn_qq_share) {
            qqShare();

        } else if (i == R.id.btn_wx_login) {
            wxLogin();

        } else if (i == R.id.btn_qq_login) {
            qqLogin();

        }
    }


    public void qqShare() {
        ShareUtils.shareWeb(this, ShareContent.url, ShareContent.title
                , ShareContent.text, ShareContent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.QQ
        );
    }

    public void wxShare() {
        ShareUtils.shareWeb(this, ShareContent.url, ShareContent.title
                , ShareContent.text, ShareContent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN
        );
    }

    public void weixinCircle() {
        ShareUtils.shareWeb(this, ShareContent.url, ShareContent.title
                , ShareContent.text, ShareContent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN_CIRCLE
        );
    }

    public void sina() {
        ShareUtils.shareWeb(this, ShareContent.url, ShareContent.title
                , ShareContent.text, ShareContent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.SINA
        );
    }

    public void Qzone() {
        ShareUtils.shareWeb(this, ShareContent.url, ShareContent.title
                , ShareContent.text, ShareContent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.QZONE
        );
    }


    public void qqLogin() {
        authorization(SHARE_MEDIA.QQ);
    }

    public void wxLogin() {
        authorization(SHARE_MEDIA.WEIXIN);
    }

    public void sinaLogin() {
        authorization(SHARE_MEDIA.SINA);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();

                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
