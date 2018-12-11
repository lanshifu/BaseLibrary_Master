package com.lanshifu.demo_module.suanfa;

/**
 * Created by lanshifu on 2018/12/6.
 */

public class SimpleTest {

    public static void main(){
        SimpleLinkList<String> simpleLinkList = new SimpleLinkList<String>();
        simpleLinkList.add("1");
        simpleLinkList.add("2");
        simpleLinkList.add("3");
        simpleLinkList.add("4");
        simpleLinkList.add(1,"10");
        System.out.println("遍历：");
//        simpleLinkList.print();
        System.out.println("get:");
        System.out.println(simpleLinkList.get(0));
        System.out.println(simpleLinkList.get(1));
        System.out.println(simpleLinkList.get(2));
        System.out.println(simpleLinkList.get(3));
        System.out.println(simpleLinkList.get(4));
        System.out.println("remove:1");
        simpleLinkList.remove(1);
        simpleLinkList.print();

        System.out.println("排序开始》》》");
        Sort sort = new Sort();
        sort.test();
        System.out.println("排序结束》》》");

        SimpleStack simpleStack = new SimpleStack();
        for (int i = 0; i < 9; i++) {
            simpleStack.push(i+1);
        }

        System.out.println("出栈：");
        for (int i = 0; i < 9; i++) {
            System.out.println(simpleStack.pop());
        }

    }
}
