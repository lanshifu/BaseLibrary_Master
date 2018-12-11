package com.lanshifu.demo_module.suanfa;

/**
 * 手写 ArrayList
 *
 *
 * Created by lanshifu on 2018/12/5.
 */

public class SimpleArrayList<T> {

    //数组存放数据
    private T[] data;

    //存放个数
    private int count;


    //默认构造，大小8
    public SimpleArrayList(){
        this(8);
    }

    private SimpleArrayList(int size){
        data = (T[]) new Object[size];
        //刚开始没有数据
        this.count = 0;
    }

    public void add(T value){
        //判断是否要扩容
        if (count == data.length){
            resize();
        }
        //赋值
        data[count ++] = value;
    }

    public void add(int index,T value){
        //判断是否要扩容
        if (count == data.length){
            resize();
        }

        for (int i = count; i >= index; i--) {
            //后移一位
            data[count +1] = data[count];
        }
        //赋值
        data[index] = value;

        count ++;

    }

    public void remove(int index){
        for (int i = index; i <= count; i++) {
            //前移一位
            data[i] = data[i +1];
        }
        count --;
    }

    public T get(int index){
        return data[index];
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public int size(){
        return count;
    }

    //扩容
    private void resize(){
        System.out.println("扩容，扩容前容量大小：" +data.length);
        //大小乘2
        int size = data.length * 2;
        T[] newArray = (T[]) new Object[size];
        for (int i = 0; i < data.length; i++) {
            newArray[i] = data[i];
        }
        data = newArray;

    }
}
