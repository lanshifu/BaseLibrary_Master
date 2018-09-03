package com.lanshifu.demo_module.bean;

import com.lanshifu.baselibrary.log.LogHelper;

public final class FinalClass {
    private String name = "name";
    public static String staticName = "staticName";

    public FinalClass(){
        LogHelper.d("FinalClass-构造方法");
        LogHelper.d("name = " + name);
        LogHelper.d("name 取到值，说明成员变量在构造方法之前初始化 ");
    }

    static {
        LogHelper.d("static 方法执行");
        LogHelper.d("staticName = " + staticName);
        LogHelper.d("staticName 取到值，说明static变量在static代码块之前初始化 ");

    }
    public void setName(String name) {
        this.name = name;
    }

}
