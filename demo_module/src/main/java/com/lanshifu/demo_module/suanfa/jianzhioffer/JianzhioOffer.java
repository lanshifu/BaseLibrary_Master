package com.lanshifu.demo_module.suanfa.jianzhioffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lanshifu on 2018/9/18.
 */

public class JianzhioOffer {

    public static void test() {

        System.out.println("剑指offer 开始》》》");
        System.out.println(jumpFloor(3));
        System.out.println(Fibonacci(39));
        System.out.println(jumpFloorII(3));
        System.out.println(RectCover(3));

        System.out.println("VerifySquenceOfBST(new int[]{4,8,6,8,12,16,14,10});" +
                VerifySquenceOfBST(new int[]{4,8,6,8,12,16,14,10}));

        FindGreatestSumOfSubArray(new int[]{2,8,1,5,9});

        FirstNotRepeatingChar("google");
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
     * 利用队列
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
     *
     * 偏难
     *
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
     *
     * 复习：
     * 前序遍历：根左右
     * 中序遍历：左根右
     * 后续遍历：左右根
     *
     * 二叉搜索树：
     * 1：空树
     * 2.左子树不空，则左子树上所有结点的值均小于它的根结点的值
     * 3：若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值
     *
     *难，主要是要知道后序遍历的特点和二叉搜索树的特点
     思路：
     已知条件：后序序列最后一个值为root；二叉搜索树左子树值都比root小，右子树值都比root大。
     1、确定root；
     2、遍历序列（除去root结点），找到第一个大于root的位置，则该位置左边为左子树，右边为右子树；
     3、遍历右子树，若发现有小于root的值，则直接返回false；
     4、分别判断左子树和右子树是否仍是二叉搜索树（即递归步骤1、2、3）。
     *
     */
    public static boolean VerifySquenceOfBST(int[] sequence) {
        //后续遍历，最后一个是根节点
        if (sequence == null || sequence.length == 0) {
            return false;
        }

        return VerifySquenceOfBST(sequence,0,sequence.length -1);


    }

    private static boolean VerifySquenceOfBST(int[] sequence,int start,int end){
        if (end-start <=1){
            return true;
        }
        int i = 0;
        int j = 0;

        //1.找到第一位比根节点大的元素，记录此位置i
        for (i = 0; i < end; i++) {
            if (sequence[i] > sequence[end]){
                break;
            }
        }
        //2.检查右子树是否都大于跟节点（从第i位开始，到根节点前）
        for (j = i; j < end; j++) {
            if (sequence[j] < sequence[end]){
                return false;
            }
        }
        //3.递归判断左右子树是否都属于二叉搜索树
        boolean left = true;
        boolean right = true;
        if (i > 0) {
            left = VerifySquenceOfBST(sequence, start, i-1);
        }
        if (i < sequence.length - 1) {
            right = VerifySquenceOfBST(sequence,i,end -1);
        }
        return left && right;

    }


    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
     * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     *
     * 1.用map，时间复杂度最坏的情况下为2n，遍历两次
     *
     * 2.利用 ++ --，一次循环找到次数超过一半的数
     */
    public int MoreThanHalfNum_Solution(int [] array) {

        //1.如果次数超过一半，通过++ 和 -- ,最后result可能是结果，也可能是最后的数
        int result = array[0];
        int time = 0;
        for (int i = 0; i < array.length; i++) {
            if (time == 0){
                result = array[i];
                time  = 1;
            }else if (result == array[i]){
                time ++;
            }else {
                time --;
            }

        }
        //验证一下result 是否超过一半
        time =0;
        for (int i = 0; i < array.length; i++) {
            if (result == array[i]){
                time ++;
            }
        }
        if (time > (array.length /2)){
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
     *
     * 思路：排序
     *
     * 时间复杂度 n * k
     *
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> resultList = new ArrayList<>();
        if (k < input.length){
            return resultList;
        }
        int min = Integer.MAX_VALUE;
        while (k >0){
            for (int i : input) {
                if (min > i && !resultList.contains(i)){
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
        if (array.length ==1){
            return array[0];
        }
        int result = array[0];

        for (int i = 0; i < array.length; i++) {
            int cur = array[i];
            if (result < cur){
                result = cur;
            }
            for (int j = i+1; j < array.length; j++) {
                //计算所有可能
                cur += array[j];
                if (result < cur){
                    result = cur;
                }

            }
        }
        System.out.println("FindGreatestSumOfSubArray:" + result);
        return result;
    }

    /**
     输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)
     todo
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {

        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        //思路，遍历所有节点路径，如果连起来值是target则保存起来，最后排序一下
        if (root == null){
            return null;
        }

        while (root.left != null){

        }

        return resultList;
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

        if (root == null){
            return 0;
        }

        if (root.left == null && root.right == null){
            return 1;
        }
        if (root.left != null){
            leftDepth ++;
        }
        if (root.right != null){
            rightDepth ++;
        }
        //递归获取左右树最深度
        leftDepth += TreeDepth(root.left);
        rightDepth += TreeDepth(root.right);
        result =  leftDepth > rightDepth ? leftDepth : rightDepth;
        return result;
    }


    /**
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
     * 如果没有则返回 -1（需要区分大小写
     */
    public static int FirstNotRepeatingChar(String str) {
        //思路：遍历，用一个数组记录下标
        //  1 2 1 3 2 4 1


        //  1 1 2 1 2 1 3
        char[] chars = str.toCharArray();
        int[] arrs = new int[chars.length];
        //记录 char - count
        HashMap<Character,Integer> map = new HashMap<>();
        //记录 index - count
        HashMap<Integer,Integer> indexCountmap = new HashMap<>();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (map.containsKey(c)){
                arrs[i] = map.get(c);
                map.put(c,(arrs[i] +1));
                System.out.println("map add " + c + ":"+ (arrs[i] +1));
            }else {
                arrs[i] = 1;
                map.put(c,1);
                System.out.println("map add " + c + ":"+ 1);
            }
        }

        int result = -1;
        for (int i = 0; i < arrs.length; i++) {
            if (arrs[i] == 1){
                result = i;
                break;
            }
        }

        System.out.println("result " + result);
        return result;
    }

}
