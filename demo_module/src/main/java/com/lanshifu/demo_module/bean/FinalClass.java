package com.lanshifu.demo_module.bean;

import com.lanshifu.baselibrary.log.LogUtil;

public final class FinalClass {
    private String name = "name";
    public static String staticName = "staticName";

    public FinalClass(){
        LogUtil.d("FinalClass-构造方法");
        LogUtil.d("name = " + name);
        LogUtil.d("name 取到值，说明成员变量在构造方法之前初始化 ");
    }

    static {
        LogUtil.d("static 方法执行");
        LogUtil.d("staticName = " + staticName);
        LogUtil.d("staticName 取到值，说明static变量在static代码块之前初始化 ");

    }
    public void setName(String name) {
        this.name = name;
    }

}
