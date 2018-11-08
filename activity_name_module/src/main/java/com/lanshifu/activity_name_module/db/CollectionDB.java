package com.lanshifu.activity_name_module.db;

import org.litepal.crud.LitePalSupport;

/**
 * 自动收能量记录
 * 时间、次数
 * Created by lanshifu on 2018/11/2.
 */

public class CollectionDB extends LitePalSupport {

    public int id;
    public long time;
    public int count;

    public CollectionDB(long time, int count) {
        this.time = time;
        this.count = count;
    }
}
