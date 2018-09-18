package com.lanshifu.demo_module.suanfa.leetcode.easy;

import java.util.Stack;

/**
 * 用栈实现队列
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 * <p>
 * 示例:
 * MyQueue queue = new MyQueue();
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 */
public class MyQueue {

    //用两个栈，放入第一个栈，取出从第二个栈，第二个栈为空则把第一个栈移到第二个栈中去
    Stack<Integer> mStackOne;
    Stack<Integer> mStackTwo;

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {
        mStackOne = new Stack();
        mStackTwo = new Stack();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        mStackOne.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        //栈1转到栈2
        if (mStackTwo.empty()) {
            stackOneToStackTwo();
        }

        if (mStackTwo.empty()) {
            return -1;
        }
        return mStackTwo.pop();

    }

    /**
     * Get the front element.
     */
    public int peek() {
        //栈1转到栈2
        if (mStackTwo.empty()) {
            stackOneToStackTwo();
        }
        if (mStackTwo.empty()) {
            return -1;
        }
        return mStackTwo.peek();

    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return mStackTwo.empty() && mStackOne.empty();
    }

    public void stackOneToStackTwo() {
        while (!mStackOne.empty()) {
            mStackTwo.push(mStackOne.pop());
        }
    }
}
