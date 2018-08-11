package com.lanshifu.video_module.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanshifu.baselibrary.base.BaseTitleBarActivity;
import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.commonservice.RouterHub;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.R2;
import com.lanshifu.video_module.ui.fragment.VideoMainFragment;

import cn.jzvd.JZVideoPlayer;

@Route(path = RouterHub.VIDEO_MAIN_ACTIVITY)
public class VideoMainActivity extends BaseTitleBarActivity {


    VideoMainFragment videoMainFragment;

    @Override
    protected void initView(Bundle bundle) {

        videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentById(R.id.video_main_fragment);
        setTitleText("91视频");

        showDialog();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_activity_main;
    }

    @Override
    protected BaseView bindPresenterAndView() {
        return null;
    }

    @Override
    protected int getTBMenusId() {
        return R.menu.video_menu_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R2.id.type_all:
                videoMainFragment.setType(-1);
                break;
            case R2.id.type_long:
                videoMainFragment.setType(4);
                break;

            case R2.id.type_top:
                videoMainFragment.setType(0);
                break;
            case R2.id.type_tf:
                videoMainFragment.setType(2);
                break;
            case R2.id.type_mf:
                videoMainFragment.setType(1);
                break;
            case R2.id.type_rf:
                videoMainFragment.setType(3);
                break;
            case R2.id.download_dwtail:
                startActivity(VideoDownLoadDetailActivity.class);
                break;
        }
        setTitleText("91视频（" + item.getTitle() + ")");
        return super.onOptionsItemSelected(item);
    }


    private void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle("FBI")
                .setMessage("未成年的小鸡鸡请回避哦")
                .setCancelable(false)
                .setPositiveButton("刚好18岁", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("我还是个孩子", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }
}
