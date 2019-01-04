package com.lanshifu.demo_module.suanfa;

public class TreeTest {

    /**
     * 树结构
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
     * 前序遍历，中左右
     * @param treeNode
     */
    public void preorder(TreeNode treeNode){
        if (treeNode == null){
            return;
        }
        System.out.println(treeNode.val + " ");
        preorder(treeNode.left);
        preorder(treeNode.right);
    }

    /**
     * 中序遍历， 左中右
     */
    public void midOrder(TreeNode treeNode){
        if (treeNode == null){
            return;
        }
        midOrder(treeNode.left);
        System.out.println(treeNode.val + " ");
        midOrder(treeNode.right);

    }

    /**
     * 后序遍历， 左右中
     */
    public void afterOrder(TreeNode treeNode){
        if (treeNode == null){
            return;
        }
        afterOrder(treeNode.left);
        afterOrder(treeNode.right);
        System.out.println(treeNode.val + " ");

    }

    /**
     * 求树的高度
     * @param treeNode
     * @return
     */
    public int maxDepth(TreeNode treeNode){
        //分别遍历左右子树，比较谁高，+1

        if (treeNode == null){
            return 0;
        }

        int left = maxDepth(treeNode.left);
        int right = maxDepth(treeNode.right);

        return left > right ? (left +1) : (right +1);

    }

    /**
     * 添加节点
     */
    public void addNode(TreeNode treeNode,int value){
        //空树，直接创建一个节点
        if (treeNode == null){
            treeNode = new TreeNode(value);
        }

        if (treeNode.val == value){
            //节点一样，返回
            return;
        }

        //小的话放左边
        if (value < treeNode.val){
            if (treeNode.left == null){
                //左
                treeNode.left = new TreeNode(value);
                return;
            }
            //
            addNode(treeNode.left,value);
        }else {
            //大的话放右边
            if (treeNode.right == null){
                treeNode.right = new TreeNode(value);
                return;
            }
            addNode(treeNode.right,value);
        }

    }



}
