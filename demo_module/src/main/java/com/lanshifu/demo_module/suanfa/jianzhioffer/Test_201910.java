package com.lanshifu.demo_module.suanfa.jianzhioffer;

import java.util.ArrayList;

public class Test_201910 {

    public static void main(String[] args) {
//        printArray(selectSort(new int[]{3,5,4,2,6,7,9,8,1}));
//        printArray(insertSort(new int[]{3,5,4,2,6,7,9,8,1}));

        int[] ints = {3, 5, 4, 2, 6, 7, 9, 8, 1};
        quickSort(ints);
        printArray(ints);

        int halfNum = moreThanHalfNums(new int[]{1, 2, 4, 2, 5, 2, 2, 8});
        System.out.println("超过一半的数：" + halfNum);


        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);

        boolean symmetrical = isSymmetrical(treeNode);
        System.out.println("是否是镜像二叉树：" + symmetrical);

    }


    private static void printArray(int[] arrays) {
        System.out.println("---printArray---");
        for (int array : arrays) {
            System.out.println(array);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int[] selectSort(int[] arrays) {

        //从剩下的选最小，放到前面
        for (int i = 0; i < arrays.length; i++) {
            int min = arrays[i];
            int minIndex = i;

            //往后遍历，找最小
            for (int j = i + 1; j < arrays.length; j++) {
                if (arrays[j] < min) {
                    min = arrays[j];
                    minIndex = j;
                }
            }

            //一趟下来，交换
            if (min != i) {
                swap(arrays, i, minIndex);
            }

        }

        return arrays;

    }


    //遍历，插到前面有序的素组里面
    private static int[] insertSort(int[] arrays) {

        for (int i = 0; i < arrays.length; i++) {
            int temp = arrays[i];
            int j = i;
            while (j > 0 && arrays[j - 1] > temp) { //跟前面的比较，如果比前面小，诺一位，继续比较前面
                arrays[j] = arrays[j - 1]; //往后移动
                j--;

            }
            arrays[j] = temp;

        }

        return arrays;
    }


    public static void quickSort(int[] numbers) {
        if (numbers.length == 0) {
            return;
        }

        quickSort(numbers, 0, numbers.length - 1);

    }

    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }
    }


    private static int getMiddle(int[] numbers, int low, int high) {
        //返回一个中间值，左边比它小，右边比它大
        //挖坑填坑法

        //第一个坑的值保存起来，作为中间值
        int temp = numbers[low];
        while (low < high) {

            //右边挖坑,找到第一个比temp小的值
            while (low < high && numbers[high] > temp) {
                high--;
            }

            if (low < high) {
                numbers[low] = numbers[high]; //左边挖坑，右边拿过来填坑
            }
            //现在右边被挖了坑，需要左边拿一个来填

            while (low < high && numbers[low] < temp) {
                low++;
            }

            if (low < high) {
                numbers[high] = numbers[low];
            }

        }
        //最终low == high，是中间值，把temp填进去
        numbers[low] = temp;
        return low;

    }

    public static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }


    //合并两个有序链表
    private static Node merge(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node resultNode = new Node(0);//第一个节点不用
        Node temp = resultNode;//保存头结点，最后要返回

        while (head1 != null && head2 != null) {

            if (head1.data < head2.data) {
                resultNode.next = head1;
                head1 = head1.next;
            } else {
                resultNode.next = head2;
                head2 = head2.next;
            }

            //记得 resultNode也要跳到下个节点
            resultNode = resultNode.next;
        }

        if (head1 != null) {
            resultNode.next = head1;
        }
        if (head2 != null) {
            resultNode.next = head2;
        }

        return temp.next;

    }

    /**
     * 超过一半的数
     * 方法1：用map，key是数，value是次数
     * 方法2：有点技巧，记录
     *
     * @param nums
     * @return
     */
    private static int moreThanHalfNums(int[] nums) {

        if (nums.length == 0) {
            return -1;
        }

        int count = 1;
        int num = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                count = 1;
                num = nums[i];
                continue;
            }

            if (num == nums[i]) {
                count++;
            } else {
                count--;
            }
        }

        return num;

    }

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }


    static boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null || (pRoot.left == null && pRoot.right == null)) {
            return true;
        }

        if (pRoot.left == null) {
            return false;
        }

        if (pRoot.right == null) {
            return false;
        }

        if (pRoot.left.val != pRoot.right.val) {
            return false;
        }

        return isSymmetrical(pRoot.left) && isSymmetrical(pRoot.right);

    }

    ArrayList<ArrayList<Integer>> PrintTree(TreeNode pRoot) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot== null){
            return result;
        }




        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(pRoot.val);
        result.add(temp);


        if (pRoot.left != null){
            result.add(getLeftAndRight(pRoot.left));
        }


        return result;

    }

    ArrayList<Integer> getLeftAndRight(TreeNode pRoot){
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(pRoot.val);
        return null;

    }


}
