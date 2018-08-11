package com.lanshifu.demo_module.design_mode;

public class Test {

    void test(){

        new BuilderTest.Builder()
                .setMessage("111")
                .setTitle("222")
                .build()
                .show();



        // 命令模式

    }
}
