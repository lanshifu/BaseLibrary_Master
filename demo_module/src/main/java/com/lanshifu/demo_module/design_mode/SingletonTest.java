package com.lanshifu.demo_module.design_mode;

public class SingletonTest {

    private SingletonTest(){

    }

    private static class Holder{
        private static SingletonTest instance= new SingletonTest();
    }

    public SingletonTest getInstance(){
        return Holder.instance;
    }
}
