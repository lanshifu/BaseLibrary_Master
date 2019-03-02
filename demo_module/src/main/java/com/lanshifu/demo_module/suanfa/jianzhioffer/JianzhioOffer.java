package com.lanshifu.demo_module.suanfa.jianzhioffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lanshifu on 2018/9/18.
 */

public class JianzhioOffer {

    public static void test() {

//        System.out.println("剑指offer 开始》》》");
//        System.out.println(jumpFloor(3));
//        System.out.println(Fibonacci(39));
//        System.out.println(jumpFloorII(3));
//        System.out.println(RectCover(3));
//
//        System.out.println("VerifySquenceOfBST(new int[]{4,8,6,8,12,16,14,10});" +
//                VerifySquenceOfBST(new int[]{4, 8, 6, 8, 12, 16, 14, 10}));
//
//        FindGreatestSumOfSubArray(new int[]{2, 8, 1, 5, 9});
//
//        System.out.println(FirstNotRepeatingChar("google"));
//
//        Permutation("abc");
//
//        int[]num1 = new int[1];
//        int[]num2 = new int[1];
//        FindNumsAppearOnce(new int[]{4,6,2,2,3,3,8,8,9,9,1000000,1000000},num1,num2);

        JianzhioOffer jianzhioOffer = new JianzhioOffer();
        String printMinNumber = jianzhioOffer.PrintMinNumber(new int[]{3, 5, 1, 4, 2});
        System.out.println(printMinNumber);

        duplicate(new int[]{2, 4, 3, 1, 4}, 5, new int[1]);

        System.out.println(StrToInt("123"));
    }


    /**
     * 链表
     */
    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 树
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + (left == null ? "NULL" : left.toString()) +
                    ", right=" + (right == null ? "NULL" : right.toString()) +
                    '}';
        }
    }


    /**
     * 打输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
     *
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        if (listNode == null) {
            return list;
        }
        list.add(0, listNode.val);
        while (listNode.next != null) {
            listNode = listNode.next;
            list.add(0, listNode.val);
        }
        return list;
    }


    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     * <p>
     * 递归
     *
     * @param number
     * @return
     */
    static int jumpFloor(int number) {
        int result = 0;

        if (number == 1) {
            return 1;
        }

        if (number == 2) {
            return 2;
        }

        if (number > 2) {
            result = jumpFloor(number - 1) + jumpFloor(number - 2);
        }

        return result;

    }

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * n<=39
     * 0 1 1 2 3 5 8 13  F(n)=F(n-1)+F(n-2)
     */
    static int Fibonacci(int n) {
        int result = 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        result = Fibonacci(n - 1) + Fibonacci(n - 2);

        return result;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     *
     * @param number
     * @return
     */
    static int jumpFloorII(int number) {

        int result = 0;
        if (number == 1) {
            return 1;
        }
        if (number == 2) {
            return 2;
        }
        //n-1级
        for (int i = 1; i < number; i++) {
            result += jumpFloorII(i);
        }

        //最后一级只有一种跳法，+1
        return result + 1;
    }


    /**
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     *
     * @param n
     * @return
     */
    public int NumberOf1(int n) {
        int result = 0;
        //转换成二进制
        String binaryString = Integer.toBinaryString(n);
        System.out.println("二进制是：" + binaryString);
        for (int i = 0; i < binaryString.length(); i++) {
            String charAt = binaryString.charAt(i) + "";
            if (charAt.equals("1")) {
                result++;
            }

        }
        return result;

    }

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * 横放一个，竖着放要两个才能对齐，
     * <p>
     * 属于递归
     */
    public static int RectCover(int target) {
        int result = 0;
        //1个，只能横排
        if (target == 1) {
            return 1;
        }
        //两个,竖排或者横排，两种情况
        if (target == 2) {
            return 2;
        }
        if (target > 2) {
            //最后不是一个横的就是两个竖的，固定两种情况
            result = RectCover(target - 1) + RectCover(target - 2);
        }


        return result;
    }


    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
     * 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     *
     * @param array 2 4 6 1 3 5
     *              1 3 5 2 4 6
     */
    static void reOrderArray(int[] array) {

        if (array.length == 0) {
            return;
        }

        //第一个如果是偶数，index为0，如果是奇数，index不确定,index的值要根据第一个偶数来判断
        int index = -1;
        int i = 0;
        while (i < array.length) {
            if (array[i] % 2 != 0) {
                //确保index赋值了，也就是找的第一个偶数
                if (index != -1) {
                    int temp = array[i];
                    //1.index 之后，i之前的数，往后移动一位
                    for (int j = i; j > index; j--) {
                        array[j] = array[j - 1];
                    }
                    //2.当前这个奇数，移到index的位置，
                    array[index] = temp;
                    index++;
                }

            } else {
                //偶数，index如果是-1，给index赋值
                if (index == -1) {
                    index = i;
                }
            }
            i++;
        }

    }

    /**
     * 输入一个链表，反转链表后，输出新链表的表头。
     * 递归法
     *
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {

        //空直接返回
        if (head == null) {
            return null;
        }
        //没有next节点，返回当前节点
        if (head.next == null) {
            return head;
        }

        // 1->2->3
        // 3->2->1

        ListNode curNode = head;                    //1->2>3   2->3     3->null
        ListNode nextNode = head.next;              //2->3     3->null  3

        //用递归走到链表的末端，然后再更新每一个node的next 值 ，实现链表的反转
        ListNode preNode = ReverseList(nextNode);   //通过递归可以找到最后一个节点
        curNode.next.next = curNode;                // ?
        curNode.next = null;  //中断链表
        return preNode;

    }

    /**
     * 反转链表2，非递归
     *
     * @param head
     * @return
     */
    public ListNode ReverseList2(ListNode head) {

        //空直接返回
        if (head == null) {
            return null;
        }
        //没有next节点，返回当前节点
        if (head.next == null) {
            return head;
        }

        //记录上一个跟下一个
        ListNode preNode = null;
        ListNode nextNode = null;
        //       1->2->3->4->5
        // null<-1  2->3->4->5    第1次循环
        // null<-1<-2  3->4->5     第2次循环
        // null<-1<-2<-3  4->5     第3次循环
        // null<-1<-2<-3<-4  5     第4次循环
        // null<-1<-2<-3<-4<-5 null     第5次循环
        while (head != null) {

            nextNode = head.next; //记录下个节点
            head.next = preNode;  //指针反转

            preNode = head;       //链表移动 ，如上第1次循环
            head = nextNode;
        }

        return preNode;

    }

    /**
     * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
     * 输入 2 3   2 -3
     * 输出 8
     * <p>
     * 采用递归去相乘
     */
    public double Power(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        //exponent 大于0 或者小于0
        if (exponent > 0) {
            if (exponent == 1) {
                return base;
            }
            base *= Power(base, exponent - 1);
        } else if (exponent < 0) {
            if (exponent == -1) {
                return 1 / base;
            }
            base = (1 / base) * Power(base, exponent + 1);
        }

        return base;

    }

    /**
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
     */
    static public ListNode Merge(ListNode list1, ListNode list2) {
        //两个量链表对比，如果list1的节点小，则作为新链表节点，然后list1的next节点跟list2比较
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode newNode = null;
        if (list1.val < list2.val) {
            newNode = new ListNode(list1.val);
            newNode.next = Merge(list1.next, list2);
        } else {
            newNode = new ListNode(list2.val);
            newNode.next = Merge(list1, list2.next);
        }

        return newNode;

    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     * <p>
     * 思路：递归，左右交换
     */
    public void Mirror(TreeNode root) {

        if (root == null) {
            return;
        }

        //交换左右节点
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        //左右节点不为空，递归交换
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }

    }


    /**
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     * <p>
     * 利用队列，广度优先遍历
     * 1.先将root节点加到队列，然后while 循环从队列取出节点，判断左节点和右节点是否为空，不为空则加到队列
     */
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedBlockingDeque<>();
        //root先加入队列
        queue.add(root);

        while (!queue.isEmpty()) {
            //出队一个
            TreeNode treeNode = queue.poll();
            result.add(treeNode.val);

            if (treeNode.left != null) {
                queue.add(treeNode.left);
            }

            if (treeNode.right != null) {
                queue.add(treeNode.right);
            }
        }

        return result;

    }


    /**
     * 输入一个链表，输出该链表中倒数第k个结点。
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode FindKthToTail(ListNode head, int k) {

        //使用两个指针，第一个到达k的时候，第二个开始动
        //当第一个指针null的时候，第二个指针刚好是倒数k的位置，返回即可。
        // 存在节点小于k的情况
        if (head == null) {
            return null;
        }
        ListNode listNodek = head;
        for (int i = 1; i <= k; i++) {
            if (head != null) {
                head = head.next;
            } else {
                return null;
            }

        }

        while (head != null) {
            head = head.next;
            listNodek = listNodek.next;
        }
        return listNodek;


    }


    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     * <p>
     * 思路：
     * 1.先找到跟节点相同的A树，递归
     * 2.然后判断根节点相同，判断是否是子树
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return false;
        }

        //遍历A树，当root1的value 等于 root2的value时候，调用另一个方法递归对比
        if (root1 != null) {
            if (!(root1.val == root2.val)) {
                //节点不同左树和右树对比
                return HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
            } else {
                // root1节点值等于root2节点值，另一个递归
                if (isSameRootTree1ContainsTree2(root1, root2)) {
                    return true;
                } else {
                    //此相同跟节点的树不是子树，继续找下一个
                    return HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
                }
            }

        } else {
            return false;
        }

    }

    /**
     * 跟节点相同的树，判断是否是子树
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean isSameRootTree1ContainsTree2(TreeNode root1, TreeNode root2) {

        //root 2遍历完，
        if (root2 == null) {
            return true;
        }
        //root 2 没遍历完，root1就空，false
        if (root1 == null) {
            return false;
        }

        //两个都不空，判断val，相同则继续递归
        if (root1.val != root2.val) {
            return false;
        } else {
            return isSameRootTree1ContainsTree2(root1.left, root2.left) && isSameRootTree1ContainsTree2(root1.right, root2.right);
        }


    }


    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
     * <p>
     * 思路，利用辅助栈。如果新push 的值node小于栈顶的值，mStack2 push nodo，否则 push 前一个值
     * mStack1  5 3 2 4 1
     * mStack2  5 3 2 2 1
     */
    Stack<Integer> mStack1 = new Stack<>();
    Stack<Integer> mStack2 = new Stack<>();

    public void push(int node) {
        mStack1.push(node);
        //如果比当前最小还小，则push node，否则，push 最小temp
        if (!mStack2.isEmpty() && mStack2.peek() < node) {
            mStack2.push(mStack2.peek());
        } else {
            mStack2.push(node);
        }
        mStack1.push(node);

    }

    public void pop() {
        if (mStack1 != null && mStack2 != null) {
            mStack1.pop();
            mStack2.pop();
        }

    }

    public int top() {
        return mStack1.peek();
    }

    public int min() {
        //得到栈中最小元素,即辅助栈的栈顶元素
        return mStack2.peek();

    }


    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
     * 例如，
     * 如果输入如下4 X 4矩阵：
     * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
     * 则依次打印出数字
     * 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
     * <p>
     * 偏难
     * <p>
     * 规律：
     * 外圈 4*4
     * 00 01 02 03 左-》右
     * 13 23 33    上-》下
     * 32 31 30    右-》左边
     * 20 10       下-》上
     * <p>
     * 内圈2*2
     * 11 12  左-》右
     * 22 21  右-》左
     */
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        //记录上下左右4个点
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        ArrayList<Integer> resultList = new ArrayList<>();
        //第一种情况，至少两行两列
        while (right > left && bottom > top) {
            //左-》右
            for (int i = left; i <= right; i++) {
                resultList.add(matrix[top][i]);
            }

            //右-》下
            for (int i = top + 1; i <= bottom; i++) {
                resultList.add(matrix[i][right]);
            }

            //右下-》左
            for (int i = right - 1; i >= left; i--) {
                resultList.add(matrix[bottom][i]);
            }

            //左下-》上
            for (int i = bottom - 1; i > top; i--) {
                resultList.add(matrix[i][left]);
            }

            //缩小一圈
            left++;
            right--;
            bottom--;
            top++;
        }

        //单独剩下一行
        if ((top == bottom) && (right > left)) {
            for (int i = left; i <= right; i++) {
                resultList.add(matrix[top][i]);
            }
        }

        //单独剩下一列
        if ((left == right) && (bottom > top)) {
            for (int i = top; i <= bottom; i++) {
                resultList.add(matrix[i][left]);
            }
        }

        //单独剩下一个元素的情况
        if ((bottom == top) && (right == left)) {
            resultList.add(matrix[left][bottom]);
        }

        return resultList;


    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
     * <p>
     * 复习：
     * 前序遍历：根左右
     * 中序遍历：左根右
     * 后续遍历：左右根
     * <p>
     * 二叉搜索树：
     * 1：空树
     * 2.左子树不空，则左子树上所有结点的值均小于它的根结点的值
     * 3：若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值
     * <p>
     * 难，主要是要知道后序遍历的特点和二叉搜索树的特点
     * 思路：
     * 已知条件：后序序列最后一个值为root；二叉搜索树左子树值都比root小，右子树值都比root大。
     * 1、确定root；
     * 2、遍历序列（除去root结点），找到第一个大于root的位置，则该位置左边为左子树，右边为右子树；
     * 3、遍历右子树，若发现有小于root的值，则直接返回false；
     * 4、分别判断左子树和右子树是否仍是二叉搜索树（即递归步骤1、2、3）。
     */
    public static boolean VerifySquenceOfBST(int[] sequence) {
        //后续遍历，最后一个是根节点
        if (sequence == null || sequence.length == 0) {
            return false;
        }

        return VerifySquenceOfBST(sequence, 0, sequence.length - 1);


    }

    private static boolean VerifySquenceOfBST(int[] sequence, int start, int end) {
        if (end - start <= 1) {
            return true;
        }
        int i = 0;
        int j = 0;

        //1.找到第一位比根节点大的元素，记录此位置i
        for (i = 0; i < end; i++) {
            if (sequence[i] > sequence[end]) {
                break;
            }
        }
        //2.检查右子树是否都大于跟节点（从第i位开始，到根节点前）
        for (j = i; j < end; j++) {
            if (sequence[j] < sequence[end]) {
                return false;
            }
        }
        //3.递归判断左右子树是否都属于二叉搜索树
        boolean left = true;
        boolean right = true;
        if (i > 0) {
            left = VerifySquenceOfBST(sequence, start, i - 1);
        }
        if (i < sequence.length - 1) {
            right = VerifySquenceOfBST(sequence, i, end - 1);
        }
        return left && right;

    }


    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
     * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     * <p>
     * 1.用map，时间复杂度最坏的情况下为2n，遍历两次
     * <p>
     * 2.利用 ++ --，一次循环找到次数超过一半的数
     */
    public int MoreThanHalfNum_Solution(int[] array) {

        //1.如果次数超过一半，通过++ 和 -- ,最后result可能是结果，也可能是最后的数
        int result = array[0];
        int time = 0;
        for (int i = 0; i < array.length; i++) {
            if (time == 0) {
                result = array[i];
                time = 1;
            } else if (result == array[i]) {
                time++;
            } else {
                time--;
            }

        }
        //验证一下result 是否超过一半
        time = 0;
        for (int i = 0; i < array.length; i++) {
            if (result == array[i]) {
                time++;
            }
        }
        if (time > (array.length / 2)) {
            return result;
        }
        return 0;

        //二.利用HashMap
//        HashMap<Integer, Integer> resultMap = new HashMap<>();
//        //超过一半
//        int minCount = array.length /2;
//        for (int i = 0; i < array.length; i++) {
//            int key = array[i];
//            Integer integer = resultMap.get(key);
//            if (integer !=null){
//                resultMap.put(key, ++integer);
//            }else {
//                resultMap.put(key,1);
//            }
//
//        }
//        //循环之后，次数都保存到map中，遍历即可
//        for (Integer key : resultMap.keySet()) {
//            if (resultMap.get(key) > minCount){
//                return key;
//            }
//        }
//        return 0;

    }

    /**
     * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
     * <p>
     * 思路：排序
     * <p>
     * 时间复杂度 n * k
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> resultList = new ArrayList<>();
        if (k < input.length) {
            return resultList;
        }
        int min = Integer.MAX_VALUE;
        while (k > 0) {
            for (int i : input) {
                if (min > i && !resultList.contains(i)) {
                    min = i;
                }
            }

            resultList.add(min);
            min = Integer.MAX_VALUE;
            k--;
        }

        return resultList;

    }


    /**
     * 计给一个数组，返回它的最大连续子序列的和
     * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)
     */
    public static int FindGreatestSumOfSubArray(int[] array) {
        if (array.length == 1) {
            return array[0];
        }
        int result = array[0];

        for (int i = 0; i < array.length; i++) {
            int cur = array[i];
            if (result < cur) {
                result = cur;
            }
            for (int j = i + 1; j < array.length; j++) {
                //计算所有可能
                cur += array[j];
                if (result < cur) {
                    result = cur;
                }

            }
        }
        System.out.println("FindGreatestSumOfSubArray:" + result);
        return result;
    }


    /**
     * 输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
     */
    public int TreeDepth(TreeNode root) {
        int result = 0;
        //左树深度
        int leftDepth = 0;
        //右树深度
        int rightDepth = 0;

        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left != null) {
            leftDepth++;
        }
        if (root.right != null) {
            rightDepth++;
        }
        //递归获取左右树最深度
        leftDepth += TreeDepth(root.left);
        rightDepth += TreeDepth(root.right);
        result = leftDepth > rightDepth ? leftDepth : rightDepth;
        return result;
    }


    /**
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
     * 如果没有则返回 -1（需要区分大小写
     */
    public static int FirstNotRepeatingChar(String str) {
        //思路：利用每个字母的ASCII码作hash来作为数组的index
        //  1 2 1 3 2 4 1
        //  1 1 2 1 2 1 3
        if (str.length() == 0) {
            return -1;
        }
        char[] chars = str.toCharArray();
        int[] arrs = new int[256];
        //用一个数组，
        for (int i = 0; i < chars.length; i++) {
            arrs[chars[i]]++;
        }
        for (int i = 0; i < arrs.length; i++) {
            if (arrs[chars[i]] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c
     * 所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     * 输入描述:输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     */
    public static ArrayList<String> Permutation(String str) {

        //可以用递归，先取首个字符，然后去掉首个字符再递归
        ArrayList<String> resultList = new ArrayList<>();
        if (str.length() == 0) {
            return resultList;
        }
        //递归调用，字符串，
        Permutation(str, "", resultList);
        System.out.println(resultList.size());
        return resultList;
    }

    //递归
    public static void Permutation(String str, String result, ArrayList<String> resultList) {
        //递归尽头，字符串只有一个字符的时候
        if (str.length() == 1) {
            result = result + str;
            System.out.println(result);
            if (!resultList.contains(result)) {
                resultList.add(result);
            }
            return;
        }

        //遍历字符串
        for (int i = 0; i < str.length(); i++) {
            //取出一个，然后字符串去掉这个字符生成新的字符串，递归
            char charAt = str.charAt(i);
            String newString = removeCharAt(str, i);
            Permutation(newString, result + charAt, resultList);

        }

    }

    //字符串去掉某个字符
    public static String removeCharAt(String s, int pos) {
        // 使用substring()方法截取0-pos之间的字符串+pos之后的字符串，相当于将要把要删除的字符串删除
        return s.substring(0, pos) + s.substring(pos + 1);
    }


    int currentNumber = 0; //路径值相加
    ArrayList<Integer> currentNumberList = new ArrayList<>();  //路径值的list

    /**
     * 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     *
     * @param root
     * @param target 输入 {10,5,12,4,7},22
     *               输出 [[10,5,7],[10,12]]
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (root == null) {
            return result;
        }
        //递归的思路啊，从根节点往下遍历，遇到节点值加起来等于target则add到list
        FindPath(root, target, result);
        return result;
    }

    private void FindPath(TreeNode root, int target, ArrayList<ArrayList<Integer>> result) {
        currentNumberList.add(root.val); //
        currentNumber += root.val;        //
        //target条件满足，并且是叶子节点
        if (currentNumber == target && root.left == null && root.right == null) {
            //这里要 ArrayList， currentNumberList是会变的
            result.add(new ArrayList<>(currentNumberList));
        }

        //遍历左右子树
        if (currentNumber < target && root.left != null) {
            FindPath(root.left, target, result);
        }

        if (currentNumber < target && root.right != null) {
            FindPath(root.right, target, result);
        }

        //遍历完一条路径之后回退
        currentNumber -= root.val;
        currentNumberList.remove((Integer) root.val);

    }

    /**
     * 输入两个链表，找出它们的第一个公共结点。
     * 公共节点一定在尾部，
     * 1.可以利用两个栈，先入栈，然后尾部比较
     * 2.先比较长度，然后长的先走若干步，然后比较
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return null;
        }
        int length1 = getNodeLength(pHead1);
        int length2 = getNodeLength(pHead2);
        ListNode longNode = length1 > length2 ? pHead1 : pHead2;
        ListNode shortNode = length1 < length2 ? pHead1 : pHead2;
        int lengttth = length1 > length2 ? length1 - length2 : length2 - length1;
        for (int i = 0; i < lengttth; i++) {
            longNode = longNode.next;
        }
        while (longNode != null) {
            if (longNode.val == shortNode.val) {
                return longNode;
            }
            longNode = longNode.next;
            shortNode = shortNode.next;
        }

        return null;
    }

    public int getNodeLength(ListNode listNode) {
        int length = 0;
        while (listNode != null) {
            length++;
            listNode = listNode.next;
        }
        return length;
    }

    /**
     * 统计一个数字在排序数组中出现的次数
     */
    public int GetNumberOfK(int[] array, int k) {
        //方法1.遍历，事件复杂度O(n)（不会出这么容易的题的。。。）
//        int result = 0;
//        for (int i = 0; i < array.length; i++) {
//            if (array[i] == k){
//                result ++;
//            }
//        }
//        return result;

        //方法2,二分查找，先找到第一个值，然后左右去找
        //获取中值
        int index = getByMidd(array, k);
        if (index == -1) {
            return 0;
        }
        int result = 1;
        // TODO: 2018/12/5  左右比较
        return result;
    }

    /**
     * 二分查找
     *
     * @param array
     * @param k
     * @return
     */
    public int getByMidd(int[] array, int k) {
        int length = array.length;
        int start = 0;
        int end = array.length - 1;
        int midd;
        while (start <= end) {
            midd = (start + end) / 2;
            if (k > array[midd]) {
                start = midd + 1;
            } else if (start < end) {
                end = midd - 1;
            } else {
                return midd;
            }
        }
        return -1;
    }

    /**
     * 一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。请写程序找出这两个只出现一次的数字。
     *
     * @param array
     * @param num1
     * @param num2  //num1,num2分别为长度为1的数组。传出参数
     *              //将num1[0],num2[0]设置为返回结果
     */
    public static void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        // 利用 异或，得到num1 和 num2 异或结果，因为两个相同的数异或都是0, 0^num1^num2 == num1^num2
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            result = result ^ array[i];
        }
        //知道 num1^num2，怎么区分出来， 01 10 -> 11
        // 把 array 分成两组，怎么分，
        // 1、找到result 从右到左第一个出现1的位的index
        // 2、遍历数组，右移index位，如果是1则 异或num1 ，否则异或num2
        int index1 = findRightIndex1(result);
        for (int i = 0; i < array.length; i++) {
            if (isBit1(array[i], index1)) {
                num1[0] ^= array[i];
            } else {
                num2[0] ^= array[i];
            }

        }
        System.out.println(num1[0] + "，" + num2[0]);

    }

    /**
     * 找到右边开始1的下标
     *
     * @return
     */
    public static int findRightIndex1(int result) {
        if (result == 0) {
            return 0;
        }
        // 2  ->  10
        int index = 0;
        while (true) {
            if (((result & 1) != 0)) {
                return index;
            }
            index++;
            result = result >> 1; //右移一位  010 -> 001
        }

    }

    /**
     * 右边开始index位是不是1
     *
     * @param num
     * @param index
     * @return
     */
    public static boolean isBit1(int num, int index) {
        num = num >> index; //右移index位，
        return ((num & 1) != 0); //判断最右边的是不是1  111 & 001 == 001

    }

    /**
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     */
    public String PrintMinNumber(int[] numbers) {

        //两个数字m和n能拼成数字mn和nm。如果mn<nm，那么我们应该打印出mn，即m应该排在n的前面，
        // 我们此时定义m小于n；反之，如果nm<mn，我们定义n小于m。如果mn=nm，我们定义m等于n。
        // （注：符号的<，>, =是常规意义的数值大小，而文字的“大于”，“小于”，“等于”表示我们新定义的大小关系）。

        //因存在大数问题，故我们把数字转化为字符串，另外把数字m和数字n拼接起来得到mn和nm，它们的位数肯定是相同的，
        // 因此比较它们的大小只需要按照字符串大小的比较规则就可以了。
        if (numbers.length == 0) {
            return null;
        }
        String str[] = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            str[i] = numbers[i] + "";
        }
        // 1 23
        //对比 123 和 231
        //手动排序
//        String temp;
//        //记录已经排好序的
//        StringBuilder pre = new StringBuilder();
//        pre.append(str[0]);
//        for (int i = 0; i < str.length -1; i++) {
//
//            System.out.println("pre = " + pre);
//            String str1 = pre + str[i+1];
//            String str2 = str[i+1] + pre;
//
//            if (str1.compareTo(str2) > 0){
//                //右边小，替换位置
//                System.out.println("替换位置：" + str[i] + "，" + str[i+1]);
//                temp = str[i];
//                str[i] = str[i+1];
//                str[i+1] = temp;
//                //交换了，之前记录的要
//                pre.append(str[i],0,str[i].length() -1);
//            }else {
//                //没有交换，直接加在后面
//                pre.append(str[i+1]);
//            }
//
//        }

        //todo 使用排序,怎么用代码实现排序
        Arrays.sort(str, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String str1 = o1 + o2;
                String str2 = o2 + o1;
                return str1.compareTo(str2);
            }
        });


        StringBuilder sb = new StringBuilder();
        for (String aStr : str) {
            sb.append(aStr);
        }

        return sb.toString();

    }


    /**
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，
     * 输出两个数的乘积最小的
     *
     * @param array
     * @param sum
     * @return
     */
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        //递增排序的数组 i + j = sum
        //递增的，相加比较一半就行
        //方法1，比较慢
        ArrayList<Integer> result = new ArrayList<>();
//        int multesult = Integer.MAX_VALUE;//保存相乘的结果
//        for (int i = 0; i < array.length / 2; i++) {
//
//            for (int j = i + 1; j < array.length; j++) {
//
//                if (array[i] + array[j] == sum) {
//                    //相乘最小
//                    if (array[i] * array[j] < multesult) {
//                        result.clear();
//                        result.add(array[i]);
//                        result.add(array[j]);
//                        multesult = array[i] * array[j];
//                    }
//                }
//            }
//        }
//        return result;

        //方法2，夹逼准则
        if (array == null || array.length == 0)
            return result;
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int total = array[left] + array[right];
            if (total == sum) {
                result.add(array[left]);
                result.add(array[right]);
                return result;
            } else if (total > sum) {
                //大于sum，说明太大了，right左移寻找更小的数
                --right;

            } else {
                //2.如果和小于sum，说明太小了，left右移寻找更大的数
                ++left;
            }
        }
        return result;

    }


    /**
     * 求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）。
     *
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int result = 0;
        //方法1，转成String，判断contain
        for (int i = 1; i <= n; i++) {
            String s = i + "";
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    result++;
                }
            }
        }
        return result;

        //方法2，没看懂
    }


    /**
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。
     * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     * 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
     *
     * @param numbers
     * @param length
     * @param duplication 返回任意重复的一个，赋值duplication[0]
     * @return
     */
    public static boolean duplicate(int numbers[], int length, int[] duplication) {

        //思路1，因为所有数字都在0到n-1的范围内，借助数组，只要遍历一次，
        //时间复杂度O(n)，空间复杂度O(n)
        int temp[] = new int[length];
        for (int i = 0; i < length; i++) {
            int number = numbers[i];
            if (temp[number] == 1) {
                duplication[0] = number;
                System.out.println("duplicate:" + number);
                return true;
            }
            temp[number] = 1;
        }
        return false;

    }


    /**
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”
     *
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString(String str, int n) {
        //思路：分割，头尾拼接

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (i < n) {
                sb1.append(str.charAt(i));
            } else {
                sb2.append(str.charAt(i));
            }
        }
        return sb2.append(sb1.toString()).toString();

    }


    boolean isBalanced = true;

    /**
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
     *
     * @param root
     * @return
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        //二叉树特点：1.空树。2 左右子树高度差不超过1
        //1.采用递归
        //2.优化，判断高度差超过1直接原路返回，之后的不用再判断
        if (root == null) {
            return true;
        }

        getTreeDeep(root);

        return isBalanced;
    }

    public int getTreeDeep(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = getTreeDeep(root.left) + 1;
        int rightDeep = getTreeDeep(root.right) + 1;
        //左右子树高度超过1
        if (leftDeep - rightDeep > 1 || rightDeep - leftDeep > 1) {
            isBalanced = false;
        }
        return leftDeep > rightDeep ? leftDeep : rightDeep;

    }


    /**
     * 反转单词顺序
     * “student. a am I”   ->  I am a student.
     *
     * @param str
     * @return
     */
    public String ReverseSentence(String str) {

        if (str.trim().equals("")) {
            return str;
        }
        //思路1，可以用个栈
        //思路2，从尾部遍历，放到sb中
        String[] split = str.split(" ");
        StringBuilder sb = new StringBuilder();

        //1
//        Stack<String> stack = new Stack<>();
//        for (String s : split) {
//            stack.push(s);
//        }
//        while (!stack.empty()){
//            sb.append(stack.pop());
//            if (!stack.empty()){
//                sb.append(" ");
//            }
//        }

        //2
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(split[i]);
            if (i > 0) {
                sb.append(" ");
            }
        }

        return sb.toString();

    }


    TreeNode pre = null;  //上一个节点
    TreeNode head = null; //头结点

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
     *
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {

        //思路：排序，即中序遍历,左->中->右
        if (pRootOfTree == null) {
            return null;
        }

        Convert(pRootOfTree.left);

        if (head == null) {
            head = pRootOfTree;
            pre = pRootOfTree;
        } else {
            //当前节点的left指向上一个，上一个的right指向当前，上一个变成当前
            pRootOfTree.left = pre;
            pre.right = pRootOfTree;
            pre = pRootOfTree;
        }

        Convert(pRootOfTree.right);

        return head;
    }


    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     *
     * @param num1
     * @param num2
     * @return
     */
    public int Add(int num1, int num2) {
        //方法1：用到++ --，好像不符合
//        int result = num1;
//        while (num2 >0){
//            num2 --;
//            result ++;
//        }
//        while (num2 <0){
//            num2 ++;
//            result--;
//        }
//        return result;

        //方法2：位运算
        //两个数异或：相当于每一位相加，而不考虑进位；
        //两个数相与，并左移一位：相当于求得进位；
        //将上述两步的结果相加
        while (num2 != 0) {
            int sum = num1 ^ num2;
            int carray = (num1 & num2) << 1;
            num1 = sum;
            num2 = carray;
        }
        return num1;
    }


    /**
     * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
     * 连续正数和为100的序列:18,19,20,21,22
     *
     * @param sum
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {

        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        for (int i = 1; i < sum; i++) {
            int current = i;
            int result = 0;
            boolean isContinuous = true;
            ArrayList<Integer> list = new ArrayList<>();
            while (isContinuous) {
                result += current;
                list.add(current);

                //满足
                if (result == sum) {
                    resultList.add(list);
                    isContinuous = false;
                }
                //超过
                if (result > sum) {
                    isContinuous = false;
                }
                current++;
            }
        }

        return resultList;
        //方法2：找规律
    }


    /**
     * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
     * 例如，链表1->1->2->3->3->4->4->5 处理后为 1->2->5
     *
     * @param pHead
     * @return
     */
    public ListNode deleteDuplication(ListNode pHead) {

        if (pHead == null) {
            return null;
        }

        //因为可以第一二个相同，所以new一个ListNode，作为头结点
        ListNode preNodeHead = new ListNode(-1);
        preNodeHead.next = pHead;
        ListNode preNode = preNodeHead;  //这个代表上一个节点，用于移动和删除

        ListNode currNode = pHead.next;//先走一步;
        int preValue = pHead.val;  //保存上一个节点的值
        boolean canDelete = false; //是否可以删除
        while (currNode != null) {
            if (preValue == currNode.val) {
                //值相同，preNode指针不动
                canDelete = true;
            } else {
                if (canDelete) {
                    //删除节点，preNode指针指向当前
                    preNode.next = currNode;
                    canDelete = false;
                } else {
                    //不相同，不需要删除，preNode指针 前移
                    preNode = preNode.next;
                }

            }
            //更新上一个节点的值
            preValue = currNode.val;
            currNode = currNode.next;
        }

        //最后相同的情况，不会进while删除，所以在后面删除
        if (canDelete) {
            preNode.next = null;
        }

        //最后返回 preNode.next，因为第一个节点是创建的
        return preNodeHead.next;

    }


    /**
     * 将一个字符串转换成一个整数
     * 数值为0或者字符串不是一个合法的数值则返回0
     *
     * @param str
     * @return
     */
    public static int StrToInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        //判断正负,去掉符号位
        boolean isPositive = true;
        if (str.charAt(0) == '-') {
            isPositive = false;
            str = str.substring(1);
        } else if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        if (str.length() == 0) {
            return 0;
        }

        int result = -1;
        for (int i = 0; i < str.length(); i++) {

            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                //1:49   2:50
                if (result == -1) {
                    result = charAt - '0';
                } else {
                    result = result * 10 + (charAt - '0');
                }

            } else {
                //非法
                return 0;
            }
        }

        if (isPositive) {
            return result;
        } else {
            return -result;
        }

    }


    int index = 0;
    TreeNode KthNode;

    /**
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）中，
     * 按结点数值大小顺序第三小结点的值为4
     *
     * @param pRoot
     * @param k
     * @return
     */
    TreeNode KthNode(TreeNode pRoot, int k) {
        //中序遍历即排序，左中右
        KthNode2(pRoot, k);
        return KthNode;
    }

    void KthNode2(TreeNode pRoot, int k) {
        if (pRoot == null) {
            return;
        }

        if (pRoot.left != null) {
            KthNode2(pRoot.left, k);
        }
        //中序遍历，第k个就返回
        index++;
        if (index == k) {
            KthNode = pRoot;
            return;
        }

        if (pRoot.right != null) {
            KthNode2(pRoot.right, k);
        }

    }


    /**
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        // 1-2-3-4-5-6-4-5-6-...
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            //指针相遇，这时候还不能确定入口
            if (slow == fast) {
                //fast所经过节点数为2x,slow所经过节点数为x,设环中有n个节点，
                //fast比slow多走r圈有2x=rn+x; x=rn;（r为圈数，n为一圈的结点数），
                //即slow刚好走了一圈的节点数
                //此时fast指向head，再一起走，相遇的点就是入口，公式得出...
                fast = pHead;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;

    }


    /**
     * 请实现一个函数，用来判断一颗二叉树是不是对称的。
     * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     *
     * @param pRoot
     * @return
     */
    boolean isSymmetrical(TreeNode pRoot) {
        //    1
        //  2   2
        // 3 4 4 3
        //对节点的左孩子与其兄弟节点右孩子的判断以及对节点右孩子与其兄弟节点左孩子的判断

        if (pRoot == null) {
            return true;
        }

        return isSymmetrical(pRoot.left, pRoot.right);

    }

    boolean isSymmetrical(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null) {
            return true;
        }
        if (leftNode == null || rightNode == null) {
            return false;
        }
        if (leftNode.val == rightNode.val) {
            return isSymmetrical(leftNode.left, rightNode.right) && isSymmetrical(leftNode.right, rightNode.left);
        } else {
            return false;
        }

    }


    /**
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     *
     * @param pRoot
     * @return
     */
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        //    1
        //  2   2
        // 3 4 4 3

        //1.队列实现，一层一层放到队列
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        if (pRoot == null) {
            return resultList;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        while (!queue.isEmpty()) {
            int size = queue.size();
            //队列中所有value都取出来，同时把下一层加到队列
            ArrayList<Integer> inerList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                //peek 获取队列第一个元素，将左右子树添加到队列
                TreeNode treeNode = queue.peek();
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
                //poll 移除队列第一个元素
                inerList.add(queue.poll().val);
            }
            resultList.add(inerList);

        }

        //2.递归实现
        //depth(pRoot,1,resultList);


        return resultList;

    }

    //递归实现
    private void depth(TreeNode treeNode, int depth, ArrayList<ArrayList<Integer>> resultList) {
        if (treeNode == null) {
            return;
        }
        if (depth > resultList.size()) {
            resultList.add(new ArrayList<>());
        }
        //动态扩容，添加到内部list
        resultList.get(depth).add(treeNode.val);
        depth(treeNode.left, depth + 1, resultList);
        depth(treeNode.right, depth + 1, resultList);

    }

    /**
     * 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，
     * 第三行按照从左到右的顺序打印，其他行以此类推。
     *
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> PrintZ(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        //利用栈
        if (pRoot == null) {
            return resultList;
        }

        //两个栈，奇偶数分开
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        int depth = 1;

        stack1.push(pRoot);
        while (!stack1.empty() || !stack2.empty()) {
            ArrayList<Integer> inerList = new ArrayList<>();

            if (depth % 2 != 0) { //单数行
                while (!stack1.empty()) {
                    TreeNode peek = stack1.peek();
                    if (peek.left != null) {
                        stack2.push(peek.left);
                    }
                    if (peek.right != null) {
                        stack2.push(peek.right);
                    }
                    inerList.add(stack1.pop().val);
                }

            } else {//双数行
                while (!stack2.empty()) {
                    TreeNode peek = stack2.peek();
                    if (peek.right != null) {
                        stack1.push(peek.right);
                    }
                    if (peek.left != null) {
                        stack1.push(peek.left);
                    }
                    inerList.add(stack2.pop().val);
                }

            }
            resultList.add(inerList);
            depth++;
        }
        return resultList;

    }

}
