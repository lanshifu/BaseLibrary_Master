package com.lanshifu.demo_module.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.xxpermission.OnPermission;
import com.lanshifu.demo_module.xxpermission.Permission;
import com.lanshifu.demo_module.xxpermission.XXPermissions;

import java.util.List;

public class DemoXXPermissionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_xxpermission);
    }



    public void requestPermission(View view) {
        XXPermissions.with(this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(Permission.Group.STORAGE, Permission.Group.CALENDAR)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            ToastUtil.showShortToast("获取权限成功");
                        }else {
                            ToastUtil.showShortToast("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if(quick) {
                            ToastUtil.showShortToast("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(DemoXXPermissionActivity.this);
                        }else {
                            ToastUtil.showShortToast("获取权限失败");
                        }
                    }
                });
    }

    public void isHasPermission(View view) {
        if (XXPermissions.isHasPermission(this, Permission.Group.STORAGE)) {
            ToastUtil.showShortToast("已经获取到权限，不需要再次申请了");
        }else {
            ToastUtil.showShortToast("还没有获取到权限或者部分权限未授予");
        }
    }

    public void gotoPermissionSettings(View view) {
        XXPermissions.gotoPermissionSettings(DemoXXPermissionActivity.this);
    }
}
