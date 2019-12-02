package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;

public class DemoHookTestActivity extends BaseTitleBarActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.demo_activity_hooktest;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("Frida 测试");

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testHook();
            }
        });
    }

    private void testHook() {
        MyClass myClass = new MyClass();

        MyClass myClass2 = new MyClass(new int[]{1, 2});
        MyClass myClass3 = new MyClass(1, 2);
        myClass3.method1();
        myClass3.method2(1, 2);
    }


    static class MyClass {

        private static final String TAG = "MyClass";

        public MyClass() {
            Log.d(TAG, "无参构造: ");
        }

        public MyClass(int[] i) {
            Log.d(TAG, "有参构造: " + i[0]);
        }

        public MyClass(int i, int j) {
            Log.d(TAG, "有参构造: " + i + "," + j);
        }

        public void method1() {
            Log.d(TAG, "void method1 ");
        }

        public int method2(int i, int j) {
            Log.d(TAG, " method2 ");
            return i + j;

        }

        public int method3(int i, int j, int k) {
            Log.d(TAG, " method3 ");
            return i + j + k;

        }
    }
}
