package com.lanshifu.demo_module.suanfa.leetcode.easy;

import java.util.Arrays;

/**
 * 不使用任何内建的哈希表库设计一个哈希集合
  所有的值都在 [1, 1000000]的范围内。      --------》 固定大小数组
 操作的总数目在[1, 10000]范围内。
 不要使用内建的哈希集合库。
 具体地说，你的设计应该包含以下的功能

 add(value)：向哈希集合中插入一个值。
 contains(value) ：返回哈希集合中是否存在这个值。
 remove(value)：将给定值从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。

 示例:
 MyHashSet hashSet = new MyHashSet();
 hashSet.add(1);
 hashSet.add(2);
 hashSet.contains(1);    // 返回 true
 hashSet.contains(3);    // 返回 false (未找到)
 hashSet.add(2);
 hashSet.contains(2);    // 返回 true
 hashSet.remove(2);
 hashSet.contains(2);    // 返回  false (已经被删除)
 */
public class MyHashSet {
    int[] arr = new int[1000005];

    /** Initialize your data structure here. */
    public MyHashSet() {

    }

    public void add(int key) {
        if (key == 0){
            arr[key] = -1;
        }else {
            arr[key] = key;
        }
    }

    /**
     * @param key
     */
    public void remove(int key) {
        arr[key] = 0;
    }

    /** Returns true if this set did not already contain the specified element */
    public boolean contains(int key) {
        if (key == 0){
            return arr[key] == -1;
        }else {
            return arr[key] != 0;
        }

    }
}
