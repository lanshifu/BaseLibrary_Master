package com.lanshifu.demo_module.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by lanshifu on 2018/10/13.
 */

public class DemoDb extends LitePalSupport {
    public DemoDb(String name, int age, long time) {
        this.name = name;
        this.age = age;
        this.time = time;
    }

    int id;
    String name;
    int age;
    long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
