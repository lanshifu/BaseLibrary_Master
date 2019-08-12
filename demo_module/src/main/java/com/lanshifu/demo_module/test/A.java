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

    void method1(int i,int ii,int iii){

    }

    void method2(String s){

    }

    void method2(String s,String ss,long l){

    }
}
