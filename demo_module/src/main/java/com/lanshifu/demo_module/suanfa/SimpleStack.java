package com.lanshifu.demo_module.suanfa;

/**
 * 手写栈
 * Created by lanshifu on 2018/12/7.
 */

public class SimpleStack {

    private int[] datas;
    private int count;
    public SimpleStack() {
        //默认栈大小为8
        datas = new int[8];
    }

    /**
     * 出栈，从尾部取
     * @return
     */
    public int pop(){
        System.out.println("出栈：");
        if (count == 0){
            System.out.println("栈空了");
            return -1;
        }

        return datas[--count];
    }

    /**
     * 入栈，判断是否满
     * @param data
     * @return
     */
    public boolean push(int data){
        System.out.println("入栈 " + data);
        if (count == datas.length){
            System.out.println("栈满了！！！");
            return false;
        }
        datas[count] = data;
        count ++;
        return true;
    }


}
