package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.lanshifu.demo_module.suanfa.niuke.Niuke;

public class DemoTestActivity extends BaseTitleBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    static class ListNode{
        int value;
        ListNode next;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("测试");

        Niuke niuke = new Niuke();
        niuke.test();

        ListNode listNode = new ListNode();
        LogHelper.d(listNode.value +"");

        ToastUtil.showShortToast("多个任务列表");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","返回拉onBackPressed");
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    protected void onBackClick() {
        Intent intent = new Intent();
        intent.putExtra("result","返回onBackClick");
        setResult(RESULT_OK,intent);
        super.onBackClick();
    }
}
