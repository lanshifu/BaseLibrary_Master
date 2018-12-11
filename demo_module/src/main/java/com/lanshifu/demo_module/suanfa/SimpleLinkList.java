package com.lanshifu.demo_module.suanfa;

/**
 * 手写单链表
 * Created by lanshifu on 2018/12/5.
 */

public class SimpleLinkList<T> {


    private Node head;
    private int size;

    //链表节点
    class Node{
        T value;

        Node next;
        public Node(T data) {
            this.value = data;
        }

    }

    public SimpleLinkList() {
        head = null;
    }

    /**
     * 添加到末尾
     * @param data
     */
    public void add(T data){
        Node inserttNode = new Node(data);
        if (head == null){
            head = inserttNode;
        }else {
            //跳到指针尾部
            Node p = head;
            while (p.next != null){
                p = p.next;
            }
            // p 是最后一个节点，next指向 新节点
            p.next = inserttNode;
        }
    }

    /**
     * 指定位置添加
     * @param index
     * @param data
     */
    public void add(int index,T data){
        Node insertNode = new Node(data);
        if (head == null){
            head = insertNode;
        }else {
            //找到index对应的前一个Node
            Node preNode = getNode(index -1);
            if (preNode == null){
                throw new IllegalArgumentException("index找不到");
            }
            //移动指针
            insertNode.next = preNode.next;
            preNode.next = insertNode;
        }
        size ++;
    }

    public int remove(int index){
        //找到index对应的前一个Node
        Node preNode = getNode(index -1);
        if (preNode == null){
            return -1;
        }
        if (preNode.next == null){
            return -1;
        }
        //直接改变指向就可以
        preNode.next = preNode.next.next;
        size --;
        return 1;
    }

    public int size(){
        return size;
    }

    private Node getNode(int index){
        Node tempNode = head;
        if (tempNode == null){
            return null;
        }
        //遍历找到index对应的Node
        int currIndex = 0;
        while (tempNode.value != null){
            if (currIndex == index){
                return tempNode;
            }
            tempNode = tempNode.next;
            currIndex ++;
        }
        return null;
    }

    public T get(int index){
        Node node = getNode(index);
        if (node == null){
            return null;
        }
        return node.value;
    }

    public void print(){

        //遍历找到index对应的Node
        Node tempNode = head;
        if (tempNode == null){
            return;
        }
        System.out.println("print:");
        while (tempNode != null && tempNode.value != null){
            System.out.println(tempNode.value + " ");
            tempNode = tempNode.next;
        }
    }

}
