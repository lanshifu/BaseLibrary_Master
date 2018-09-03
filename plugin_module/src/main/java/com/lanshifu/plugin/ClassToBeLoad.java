package com.lanshifu.plugin;

import android.util.Log;

public class ClassToBeLoad {

    public  void method(){
        Log.e("ClassToBeLoad", "called method of class " +       ClassToBeLoad.class.getName());
    }
}
