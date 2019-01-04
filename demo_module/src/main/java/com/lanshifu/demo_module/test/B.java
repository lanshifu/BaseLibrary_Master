package com.lanshifu.demo_module.test;

import java.lang.reflect.Method;

public class B extends A {

    //按顺序执行

    static{
        System.out.print(" staticB");
    }

    {
        System.out.print(" B大括号代码块");
    }

    public B(){
        System.out.print(" B");
    }

}
