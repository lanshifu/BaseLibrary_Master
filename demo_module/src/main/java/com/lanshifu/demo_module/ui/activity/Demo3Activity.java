package com.lanshifu.demo_module.ui.activity;

public class Demo3Activity extends Demo2Activity {



    //static 方法不能被继承，子类会隐藏父类的方法
    public static void staticMethod1(String stirng){

    }

    void method1() {
        staticMethod1("");
    }

}
