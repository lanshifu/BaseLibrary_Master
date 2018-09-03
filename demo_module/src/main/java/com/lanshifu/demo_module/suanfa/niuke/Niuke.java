package com.lanshifu.demo_module.suanfa.niuke;

import java.util.ArrayList;

public class Niuke {

    public void test(){
        //7,[[1,2,8,9],[2,4,9,12],[4,7,10,13],[6,8,11,15]]
        int[][] arr1 = {{1,2,8,9}, {2,4,9,12}, {4,7,10,13}, {6,8,11,15}};
        System.out.println(find(7,arr1) +"哈哈哈");

        StringBuffer stringBuilder = new StringBuffer();
        stringBuilder.append("hello");
        stringBuilder.append(" ");
        stringBuilder.append("world");
        System.out.println(replaceSpace(stringBuilder));
    }


    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
     * 判断数组中是否含有该整数。
     * @param target
     * @param array
     * @return
     */
    public static boolean find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (target == array[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     * @param str
     * @return
     */
    public String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ","%20");
    }


    public class ListNode {
        public int val;
        public ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    ArrayList<Integer> arrayList=new ArrayList<Integer>();
    /**
     * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        if (listNode == null) {
            return list;
        }
        while (listNode.next != null) {
            listNode = listNode.next;
            list.add(0, listNode.val);
        }
        return list;
        //方法2 递归，从最后一个开始add
//        if(listNode!=null){
//            this.printListFromTailToHead(listNode.next);
//            arrayList.add(listNode.val);
//        }
//        return arrayList;
    }



}
