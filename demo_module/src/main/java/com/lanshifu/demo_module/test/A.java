package com.lanshifu.demo_module.test;

public class A {

    private static A a = new A();

    {
        System.out.print(" A大括号代码块");
    }


    public A(){
        System.out.print(" A");
    }

    static{
        System.out.print(" staticA");
    }
}
