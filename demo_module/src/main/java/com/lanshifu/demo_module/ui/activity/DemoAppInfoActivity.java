package com.lanshifu.demo_module.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.R2;
import com.lanshifu.demo_module.bean.AppInfo;
import com.lanshifu.demo_module.widget.skeleton.RecyclerViewSkeletonScreen;
import com.lanshifu.demo_module.widget.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class DemoAppInfoActivity extends BaseTitleBarActivity {
    @BindView(R2.id.recyclerView)
    CommRecyclerView mRecyclerView;

    private List<AppInfo> mlistAppInfo = new ArrayList<>();
    private BaseQuickAdapter<AppInfo, BaseViewHolder> mAdapter;
    private RecyclerViewSkeletonScreen mSkeletonScreen;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected BaseView bindView() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("app信息");

        mAdapter = new BaseQuickAdapter<AppInfo, BaseViewHolder>(R.layout.demo_item_appinfo,new ArrayList<AppInfo>()) {
            @Override
            protected void convert(BaseViewHolder helper, AppInfo item) {
                helper.setImageDrawable(R.id.imgApp,item.getAppIcon());
                helper.setText(R.id.apkName,item.getAppLabel());
                helper.setText(R.id.pkgName,item.getPkgName());
                helper.setText(R.id.apkSize,item.getTotalSize());
                helper.setText(R.id.apkVersion,item.getmVersion());
                helper.setText(R.id.sigmd5,item.getSigmd5());
                helper.setText(R.id.first_act,item.getFirstActivityName());
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter);
        //骨架屏
        mSkeletonScreen = Skeleton.bind(mRecyclerView.getRecyclerView())
                .adapter(mAdapter)
                .load(R.layout.demo_list_item_loading)
                .shimmer(true)      // whether show shimmer animation.                      default is true
                .count(10)          // the recycler view item count.                        default is 10
                .color(R.color.shimmer_color)       // the shimmer color.                                   default is #a2878787
                .angle(20)          // the shimmer angle.                                   default is 20;
                .duration(1000)     // the shimmer animation duration.                      default is 1000;
                .frozen(false)
                .show();

        mRecyclerView.setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShortToast(mAdapter.getItem(position).toString());
            }
        });
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        mRecyclerView.autoRefresh();
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<AppInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AppInfo>> e) throws Exception {
                List<AppInfo> appInfos = queryAppInfo();
                e.onNext(appInfos);
            }
        }).compose(RxScheduler.<List<AppInfo>>io_main())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<List<AppInfo>>() {
                    @Override
                    public void _onNext(List<AppInfo> appInfos) {
                        mSkeletonScreen.hide();
                        mAdapter.replaceData(appInfos);
                        LogUtil.e("size "+appInfos.size());
                        showShortToast("size "+appInfos.size());
                        mRecyclerView.finishRefresh();
                        mlistAppInfo.clear();
                        mlistAppInfo.addAll(appInfos);
                    }

                    @Override
                    public void _onError(String e) {
                        mSkeletonScreen.hide();
                        showErrorToast(e);
                        mRecyclerView.finishRefresh();
                    }
                });
    }


    // 获得所有启动Activity的信息，类似于Launch界面
    public List<AppInfo> queryAppInfo() {
        PackageManager pm = this.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        if (mlistAppInfo != null) {
            mlistAppInfo.clear();
            for (ResolveInfo reInfo : resolveInfos) {
                String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
                String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名

                String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
                Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
                // 为应用程序的启动Activity 准备Intent
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(pkgName, activityName));
                // 创建一个AppInfo对象，并赋值
                AppInfo appInfo = new AppInfo();
                appInfo.setAppLabel(appLabel);
                appInfo.setPkgName(pkgName);
                appInfo.setAppIcon(icon);
                appInfo.setFirstActivityName(activityName);
                appInfo.setmVersion(getVersionName(pkgName));
                appInfo.setSigmd5(getSignMd5Str(pkgName));
                mlistAppInfo.add(appInfo); // 添加至列表中
            }
            return mlistAppInfo;
        }
        return null;
    }

    private String getVersionName(String packageName) {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName + "  Code: " + packInfo.versionCode;
        return version != null ? version : "未获取到系统版本号";
    }

    /**
     * 获取app签名md5值
     */
    public String getSignMd5Str(String packageName) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signStr = encryptionMD5(sign.toByteArray());
            return signStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }

}
