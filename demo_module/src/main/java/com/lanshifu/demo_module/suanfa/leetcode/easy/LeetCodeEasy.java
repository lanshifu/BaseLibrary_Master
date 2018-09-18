package com.lanshifu.demo_module.suanfa.leetcode.easy;

import android.annotation.SuppressLint;

import com.lanshifu.demo_module.suanfa.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LeetCodeEasy {

    public void test() {

        int[] nums = new int[]{3, 2, 4};
        int target = 6;
        twoSum(nums, target);
        lengthOfLongestSubstring("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghij");

        numJewelsInStones("aA", "aAAbbbb");
        hammingDistance(1, 4);
        judgeCircle("RLUURDDDLU");

        String[] words = new String[]{"gin", "zen", "gig", "msg"};
        uniqueMorseRepresentations(words);


        peakIndexInMountainArray(new int[]{0, 2, 1, 0});

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode1_left1 = new TreeNode(3);
        treeNode1_left1.left = new TreeNode(5);
        treeNode1.left = treeNode1_left1;
        treeNode1.right = new TreeNode(2);

        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode2_left1 = new TreeNode(1);
        treeNode2_left1.right = new TreeNode(4);
        TreeNode treeNode2_right1 = new TreeNode(3);
        treeNode2_right1.right = new TreeNode(7);
        treeNode2.left = treeNode2_left1;
        treeNode2.right = treeNode2_right1;

        TreeNode result = mergeTrees(treeNode1, treeNode2);
        System.out.println(result.toString());

        selfDividingNumbers(1, 22);

        System.out.println(maxDepth(treeNode1));

        reverseString("hello");
        reverseWords("Let's take LeetCode contest");

        findComplement(2);

        findWords(new String[]{"Hello", "Alaska", "Dad", "Peace"});

        System.out.println(canWinNim(100));

        shortestToChar("loveleetcode", 'e');

        System.out.println(hasAlternatingBits(5));

        subdomainVisits(new String[]{"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"});

        System.out.println(getSum(1, 2) + "");
        System.out.println(singleNumber(new int[]{1, 2, 3, 3, 2}));

        System.out.println(addDigits(38));


        MyHashSet hashSet = new MyHashSet();
        System.out.println("MyHashSet");
        hashSet.add(2);
        System.out.println(hashSet.contains(1));    // 返回 true
        System.out.println(hashSet.contains(3));    // 返回 false (未找到)
        hashSet.add(2);
        System.out.println(hashSet.contains(2));    // 返回 true
        hashSet.remove(2);
        System.out.println(hashSet.contains(2));// 返回  false (已经被删除)

    }

    /**
     * 定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] + nums[j] == target) {
                    System.out.print(i + "," + j);
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }


    /**
     * 给定一个字符串，找出不含有重复字符的最长子串的长度。
     * abbcdbef   -- bcdef  5
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        System.out.print("给定一个字符串，找出不含有重复字符的最长子串的长度");
        List<Integer> lengthList = new ArrayList<>();
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            List<String> list = new ArrayList<>();
            for (int j = i; j < s.length(); j++) {
                String string = s.charAt(j) + "";
                if (!list.contains(string)) {
                    list.add(string);
                } else {
                    lengthList.add(list.size());
                    break;
                }
            }
            if (!lengthList.contains(list.size())) {
                lengthList.add(list.size());
            }
            if (length < list.size()) {
                length = list.size();
            }
        }
        System.out.print(length);
        return length;

    }

    /**
     * 给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。
     * S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * 输入: J = "aA", S = "aAAbbbb"
     * 输出: 3
     */
    public int numJewelsInStones(String J, String S) {
        int result = 0;
        //宝石放到一个list里面
        List<String> jewlsList = new ArrayList<>();
        for (int i = 0; i < J.length(); i++) {
            String jewls = J.charAt(i) + "";
            jewlsList.add(jewls);
        }
        //石头在宝石list中存在，就认为是宝石
        for (int i = 0; i < S.length(); i++) {
            String c = S.charAt(i) + "";
            if (jewlsList.contains(c)) {
                result++;
            }
        }
        System.out.println(result);
        return result;
    }

    /**
     * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
     * 给出两个整数 x 和 y，计算它们之间的汉明距离。
     * 1   (0 0 0 1)
     * 4   (0 1 0 0)
     * 输出 2
     */
    public int hammingDistance(int x, int y) {
        //1转换成二进制
        String x2 = Integer.toBinaryString(x);
        String y2 = Integer.toBinaryString(y);
        //放到list里面，位数少的前面补0
        int length = x2.length() > y2.length() ? x2.length() : y2.length();
        List<String> listX = new ArrayList<>();
        List<String> listY = new ArrayList<>();
        for (int i = 0; i < x2.length(); i++) {
            String temp = x2.charAt(i) + "";
            listX.add(temp);
        }
        while (listX.size() < length) {
            listX.add(0, "0");
        }

        for (int i = 0; i < y2.length(); i++) {
            String temp = y2.charAt(i) + "";
            listY.add(temp);
        }
        while (listY.size() < length) {
            listY.add(0, "0");
        }
        //对比两个list不同的数
        int result = 0;
        for (int i = 0; i < length; i++) {
            if (!listX.get(i).equals(listY.get(i))) {
                result++;
            }
        }
        System.out.println(result);
        return result;
    }


    /**
     * 初始位置 (0, 0) 处有一个机器人。给出它的一系列动作，判断这个机器人的移动路线是否形成一个圆圈，
     * 换言之就是判断它是否会移回到原来的位置。
     * 移动顺序由一个字符串表示。每一个动作都是由一个字符来表示的。
     * 机器人有效的动作有 R（右），L（左），U（上）和 D（下）。
     * 输出应为 true 或 false，表示机器人移动路线是否成圈。
     */
    public boolean judgeCircle(String moves) {
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'R') {
                right++;
            } else if (moves.charAt(i) == 'L') {
                left++;
            } else if (moves.charAt(i) == 'U') {
                up++;
            } else if (moves.charAt(i) == 'D') {
                down++;
            }
        }
        boolean result = up == down && left == right;
        System.out.println(result);
        return result;

    }


    /**
     * 给定一个单词列表，每个单词可以写成每个字母对应摩尔斯密码的组合。例如，"cab" 可以写成 "-.-.-....-"，
     * (即 "-.-." + "-..." + ".-"字符串的结合)。我们将这样一个连接过程称作单词翻译。
     * <p>
     * 返回我们可以获得所有词不同单词翻译的数量。
     */
    public int uniqueMorseRepresentations(String[] words) {
        Map<Character, String> maplist = new HashMap<Character, String>();
        // 摩尔斯编码表集合,放到map中，key是字母，value是对应密码
        String[] values = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        for (int i = 0; i < values.length; i++) {
            maplist.put((char) ('a' + i), values[i]);
        }

        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String temp = "";
            for (int j = 0; j < word.length(); j++) {
                char chatAt = word.charAt(j);
                temp = temp + maplist.get(chatAt);
            }
            if (!resultList.contains(temp)) {
                resultList.add(temp);
            }
        }
        System.out.println(resultList.size());
        return resultList.size();

    }


    /**
     * 山脉数组的峰顶索引
     * <p>
     * 存在 0 < i < A.length - 1 使得A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
     * <p>
     * 输入：[0,1,0]
     * 输出：1
     *
     * @param A
     * @return
     */
    public int peakIndexInMountainArray(int[] A) {
        for (int i = 1; i < A.length; i++) {
            boolean largerThanBefore = true;
            boolean smallThanafter = true;
            //判断左边
            int index = i;
            while (0 < index && largerThanBefore) {
                if (A[index] < A[index - 1]) {
                    largerThanBefore = false;
                }
                index--;
            }

            //判断右边，前提是左边成立
            index = i;
            while (largerThanBefore && smallThanafter && index < A.length - 1) {
                if (A[index] < A[index + 1]) {
                    smallThanafter = false;
                }
                index++;
            }
            if (largerThanBefore && smallThanafter) {
                System.out.println(i);
                return i;
            }

        }
        return 0;
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
     * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
     * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，
     * 否则不为 NULL 的节点将直接作为新二叉树的节点。
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {

        if (t1 == null && t2 == null) {
            return null;
        }

        //节点的值
        int val = 0;
        if (t1 != null) {
            val += t1.val;
        }
        if (t2 != null) {
            val += t2.val;
        }
        TreeNode resuletTree = new TreeNode(val);

        TreeNode t1_left = null;
        TreeNode t1_right = null;
        TreeNode t2_left = null;
        TreeNode t2_right = null;

        if (t1 != null && t1.left != null) {
            t1_left = t1.left;
        }
        if (t1 != null && t1.right != null) {
            t1_right = t1.right;
        }
        if (t2 != null && t2.left != null) {
            t2_left = t2.left;
        }
        if (t2 != null && t2.right != null) {
            t2_right = t2.right;
        }

        if (t1_left != null || t2_left != null) {
            //递归给左节点赋值，前提是有左节点
            resuletTree.left = mergeTrees(t1_left, t2_left);
        }
        if (t1_right != null || t2_right != null) {
            //递归给右节点赋值
            resuletTree.right = mergeTrees(t1_right, t2_right);
        }
        return resuletTree;

    }

    /**
     * 自除数 是指可以被它包含的每一位数除尽的数。
     * 输入：
     * 上边界left = 1, 下边界right = 22
     * 输出： [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (i < 10) {
                resultList.add(i);
                continue;
            }
            String numbers = i + "";
            boolean canBeDivi = true;
            for (int j = 0; j < numbers.length(); j++) {
                Integer number = Integer.parseInt(numbers.charAt(j) + "");
                if (number == 0 || i % number != 0) {
                    canBeDivi = false;
                    break;
                }
            }
            if (canBeDivi) {
                resultList.add(i);
            }

        }

        return resultList;
    }


    /**
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {

        int result = 0;
        if (root != null) {
            //节点有数据 +1
            result++;
            int leftCount = 0;
            int rightCount = 0;
            if (root.left != null) {
                //有左节点，递归寻找子节点个数
                leftCount += maxDepth(root.left);
            }
            if (root.right != null) {
                //有左节点，递归寻找子节点个数
                rightCount += maxDepth(root.right);
            }
            //根节点 + 子节点最多的那个
            result += Math.max(leftCount, rightCount);
        }
        return result;
    }


    /**
     * 翻转二叉树
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root != null) {
            if (root.left != null && root.right != null) {
                TreeNode temp = root.left;
                root.left = root.right;
                root.right = temp;
                invertTree(root.left);
                invertTree(root.right);
            } else if (root.left != null && root.right == null) {
                TreeNode temp = root.left;
                root.left = null;
                root.right = temp;
                invertTree(root.right);
            } else if (root.left == null && root.right != null) {
                TreeNode temp = root.right;
                root.left = root.right;
                root.right = null;
                invertTree(root.left);
            }

        }
        return root;
    }


    /**
     * 反转字符串
     * <p>
     * 输入：s = "hello"
     * 返回："olleh"
     *
     * @param s
     * @return
     */
    public String reverseString(String s) {
        String result = s;
        //1.从右边开始遍历即可，比较耗时
//        for (int i = s.length() - 1; i >= 0; i--) {
//            result = result + s.charAt(i);
//        }

        //2.中间分隔,递归，substring耗时
//        if (s.length() >= 2) {
//            int middle = s.length() / 2;
//            String left = reverseString(s.substring(0, middle));
//            String right = reverseString(s.substring(middle));
//            result = right + left;
//        }

        //3.StringBuilder
//        StringBuilder sb = new StringBuilder(s);
//        result =  sb.reverse().toString();

        //4.对角值替换，耗时最短
        char[] chars = s.toCharArray();
        int i = 0;
        int j = chars.length - 1;
        while (i < j) {
            char temp = chars[i];
            chars[i++] = chars[j];
            chars[j--] = temp;
        }
        result = new String(chars);

        System.out.println(result);
        return result;
    }

    /**
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 输入: "Let's take LeetCode contest"
     * 输出: "s'teL ekat edoCteeL tsetnoc"
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String[] split = s.split(" ");
        for (int i = 0; i < split.length - 1; i++) {
            sb.append(reverseString(split[i]));
            sb.append(" ");
        }
        sb.append(reverseString(split[split.length - 1]));
        System.out.println(sb.toString());
        return sb.toString();
    }


    /**
     * 给定一个正整数，输出它的补数。补数是对该数的二进制表示取反。
     * 输入: 5
     * 输出: 2
     * 解释: 5的二进制表示为101（没有前导零位），其补数为010。所以你需要输出2。
     *
     * @param num
     * @return
     */
    public int findComplement(int num) {
        String x2 = Integer.toBinaryString(num);
        char[] charArray = x2.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '0') {
                charArray[i] = '1';
            } else if (charArray[i] == '1') {
                charArray[i] = '0';
            }
        }

        String string = new String(charArray);
        Integer integer = Integer.valueOf(string, 2);
        System.out.println(integer);
        return integer;
    }

    /**
     * 键盘行
     * 给定一个单词列表，只返回可以使用在键盘同一行的字母打印出来的单词
     * <p>
     * 输入: ["Hello", "Alaska", "Dad", "Peace"]
     * 输出: ["Alaska", "Dad"]
     */
    public String[] findWords(String[] words) {
        String keysOne = "qwertyuiop";
        String keysTow = "asdfghjkl";
        String keysThree = "zxcvbnm";

        ArrayList<String> resultList = new ArrayList<String>();

        for (int i = 0; i < words.length; i++) {
            //取出一个单词
            String word = words[i].toLowerCase();
            char[] chars = word.toCharArray();
            boolean isSameLineOne = true;
            boolean isSameLineTwo = true;
            boolean isSameLineThree = true;
            for (int j = 0; j < chars.length; j++) {
                if (!keysOne.contains(chars[j] + "")) {
                    isSameLineOne = false;
                    break;
                }
            }

            if (isSameLineOne) {
                isSameLineTwo = false;
                isSameLineThree = false;
            } else {
                for (int j = 0; j < chars.length; j++) {
                    if (!keysTow.contains(chars[j] + "")) {
                        isSameLineTwo = false;
                        break;
                    }
                }
            }

            if (isSameLineOne || isSameLineTwo) {
                isSameLineThree = false;
            } else {
                for (int j = 0; j < chars.length; j++) {
                    if (!keysThree.contains(chars[j] + "")) {
                        isSameLineThree = false;
                        break;
                    }
                }
            }

            if (isSameLineOne || isSameLineTwo || isSameLineThree) {
                resultList.add(words[i]);
            }
        }

        String[] resultArray = resultList.toArray(new String[resultList.size()]);
        for (int i = 0; i < resultArray.length; i++) {
            System.out.println(resultArray[i]);
        }
        return resultArray;
    }


    /**
     * 桌子上有一堆石头，每次你们轮流拿掉 1 - 3 块石头。 拿掉最后一块石头的人就是获胜者。
     * 你作为先手。
     * 你们是聪明人，每一步都是最优解。
     * 编写一个函数，来判断你是否可以在给定石头数量的情况下赢得游戏。
     * <p>
     * 输入: 4
     * 输出: false
     * 解释: 如果堆中有 4 块石头，那么你永远不会赢得比赛；
     * 因为无论你拿走 1 块、2 块 还是 3 块石头，最后一块石头总是会被你的朋友拿走。
     *
     * @param n
     * @return
     */
    public boolean canWinNim(int n) {

        /**
         * 这里做一个抽象，假设一推里面有n个石头，每次可以取 1-m 个石头。
         显然，如果n=m+1，那么由于一次最多只能取m个，所以，无论先取者拿走多少个，后取者都能够一次拿走剩余的物品，
         后者取胜。这里我们就有一个想法了，假设这个石头推为 (m+1)的倍数，那么第一个人取k( 1 <= k <= m)个，
         只要第二个人取 (m+1-k)个石头，那么必定状态能回到最初的状态，m+1个。因为每个人都是很聪明的，取的石头的个数一定要对自己有利。
         那么，假设最初石头推不为 (m+1)的倍数。n=（m+1）r+s，那么第一个人只要取s个石头必定能获得胜利，
         反之，如果s == 0 ，那么第一个人必输
         */
        return (n % 4) != 0;
    }


    /**
     * 翻转矩阵
     *输入：[[1,2,3],[4,5,6],[7,8,9]]
     输出：[[1,4,7],[2,5,8],[3,6,9]]
     * @param A
     * @return
     */
//    public int[][] transpose(int[][] A) {
//        for (int i = 0; i < A.length; i++) {
//        }
//    }

    /**
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树
     * <p>
     * 思路:采用二分法来创建平衡二叉树，根结点刚好为数组中间的节点，根节点的左子树的根是数组左边部分的中间节点，
     * 根节点的右子树是数据右边部分的中间节点；代码执行结果和示例给出结果不同，但满足平衡二叉树。
     */
    public TreeNode sortedArrayToBST(int[] nums) {
//        if(nums == null || nums.length == 0){
//            return null;
//        }
//        int mid = (nums.length) / 2;
//        TreeNode treeNode = new TreeNode(nums[mid]);
//        if (nums.length == 1){
//            return treeNode;
//        }
//        //分割成左右两个数组
//        int[] leftNums = new int[mid];
//        int[] rightNums = new int[nums.length - mid -1];
//        for (int i = 0,leftIndex=0,rightIndex=0; i < nums.length; i++) {
//            if (leftNums.length >0 && i <mid){
//                leftNums[leftIndex ++] = nums[i];
//            }else if (rightNums.length >0 && i > mid){
//                rightNums[rightIndex ++] = nums[i];
//            }
//        }
//        treeNode.left = sortedArrayToBST(leftNums);
//        treeNode.right = sortedArrayToBST(rightNums);
//        return treeNode;

        return getTree(nums, 0, nums.length - 1);
    }

    private TreeNode getTree(int[] nums, int left, int right) {
        // [-10,-3,0,5,9]  left=0,right=4,mid =2
        // [-10,-3]        left=0,right=1,mid =0
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        TreeNode treeNode = new TreeNode(nums[mid]);
        if (left == right) {
            return treeNode;
        }
        treeNode.left = getTree(nums, left, mid - 1);
        treeNode.right = getTree(nums, mid + 1, right);
        return treeNode;

        // copy that   roger that
    }

    /**
     * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
     * <p>
     * 输入: S = "loveleetcode", C = 'e'
     * 输出: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
     * 1.字符串 S 的长度范围为 [1, 10000]。
     * 2.C 是一个单字符，且保证是字符串 S 里的字符。
     * 3.S 和 C 中的所有字母均为小写字母。
     * <p>
     * 有点慢，
     * 方法2：遍历两次，第一次
     */
    public int[] shortestToChar(String S, char C) {
        char[] chars = S.toCharArray();
        int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int left = i;
            int right = i;
            boolean isFound = false;
            int distance = 0;
            while (!isFound) {
                //左右对比
                if ((left >= 0 && C == chars[left]) || (right < chars.length && C == chars[right])) {
                    isFound = true;
                    result[i] = distance;
                    System.out.println(distance);
                }
                distance++;
                left--;
                right++;
            }
        }
        return result;

    }

    /**
     * 给定一个正整数，检查他是否为交替位二进制数：换句话说，就是他的二进制数相邻的两个位数永不相等。
     * <p>
     * 输入: 5
     * 输出: True
     * 解释:
     * 5的二进制数是: 101
     *
     * @param n
     * @return
     */
    public boolean hasAlternatingBits(int n) {
        String string = Integer.toBinaryString(n);
        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == string.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 子域名访问计数
     * 一个网站域名，如"discuss.leetcode.com"，包含了多个子域名。作为顶级域名，常用的有"com"，
     * 下一级则有"leetcode.com"，最低的一级为"discuss.leetcode.com"。当我们访问域名"discuss.leetcode.com"时，
     * 也同时访问了其父域名"leetcode.com"以及顶级域名 "com"。
     * <p>
     * 给定一个带访问次数和域名的组合，要求分别计算每个域名被访问的次数。其格式为访问次数+空格+地址，例如："9001 discuss.leetcode.com"。
     *
     * @param cpdomains
     * @return
     */
    @SuppressLint("NewApi")
    public List<String> subdomainVisits(String[] cpdomains) {
        //key是子域名，value是访问次数
        HashMap<String, Integer> resultMap = new HashMap<>();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < cpdomains.length; i++) {
            //9001 discuss.leetcode.com
            String cpdomain = cpdomains[i];
            String[] split = cpdomain.split(" ");
            int num = Integer.parseInt(split[0]);
            //discuss.leetcode.com  -> discuss.leetcode.com   leetcode.com   com
            String domain = split[1];

            resultMap.put(domain, resultMap.getOrDefault(domain, 0) + num);
            while (domain.contains(".")) {
                domain = domain.substring(domain.indexOf(".") + 1);
                resultMap.put(domain, resultMap.getOrDefault(domain, 0) + num);
            }
        }
        for (String key : resultMap.keySet()) {
            Integer count = resultMap.get(key);
            resultList.add(count + " " + key);
            System.out.println(count + " " + key);
        }
        return resultList;

    }

    /**
     * 给定长度为 2n 的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，
     * 使得从1 到 n 的 min(ai, bi) 总和最大
     * 输入: [1,4,3,2]
     * 输出: 4
     * 解释: n 等于 2, 最大总和为 4 = min(1, 2) + min(3, 4).
     *
     * @param nums
     * @return
     */
    public int arrayPairSum(int[] nums) {
        //思路，排序，然后两个为一组
        //快速排序
        Sort.quickSort(nums, 0, nums.length - 1);
        //取奇数相加
        int result = 0;
        for (int i = 0; i < nums.length; i += 2) {
            result += nums[i];
        }
        return result;

    }


    /**
     * 不使用运算符 + 和-，计算两整数a 、b之和。
     * 示例：
     * 若 a = 1 ，b = 2，返回 3
     *
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {
        int result = a;
        while (b != 0) {
            if (b > 0) {
                result++;
                b--;
            } else {
                result--;
                b++;
            }
        }
        return result;

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点
     * 输入: head = [4,5,1,9], node = 5
     输出: [4,1,9]
     解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     * @param node
     */
//    public void deleteNode(ListNode node) {
//
//    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。
     * 找出那个只出现了一次的元素。
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * 输入: [2,2,1]
     * 输出: 1
     */
    public int singleNumber(int[] nums) {

        //排序
//       Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            boolean single = true;
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    if (nums[i] == nums[j]) {
                        single = false;
                        break;
                    }
                }
            }
            if (single) {
                return nums[i];
            }

        }
        return -1;
    }

    /**
     * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。
     * 输入: 38
     * 输出: 2
     * 解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。
     *
     * @return
     */
    public int addDigits(int num) {

//        char[] chars = (num+"").toCharArray();
//        int result = 0;
//        for (char intChar : chars) {
//            result += Integer.parseInt(intChar+"");
//        }
//        if (result >= 10){
//            return addDigits(result);
//        }
//        return result;

        //最优方法：如果是两位数
        while (num / 10 > 0) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num = num / 10;
            }
            num = sum;
        }
        return num;

    }

    /**
     * 链表的中间结点
     */
    public ListNode middleNode(ListNode head) {
//        //慢指针和快指针，快指针走完，慢指针刚好走一半
//        ListNode fast = head;
//        ListNode slow = head;
//        // 1,2,3,4,5,6]
//        while (fast != null && fast.next != null) {  //2  4  6
//            fast = fast.next.next;                        //3  5  null
//            if (fast != null){
//                slow = slow.next;                        //2  3  4
//            }
//        }
//        return slow;
        ListNode[] arr = new ListNode[100];
        int index = 0;
        arr[index] = head;
        while (head != null){
            index ++;
            head = arr[index] = head.next;
        }
        return arr[index /2];
    }

    /**
     * 删除链表中的节点
     输入: head = [4,5,1,9], node = 5
     输出: [4,1,9]
     解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     * @param node
     */
    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
    }


    /**
     * 给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
     输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
     输出: [-1,3,-1]
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        //转成list
        ArrayList<Integer> nums2list = new ArrayList<>();
        for (int aNums2 : nums2) {
            nums2list.add(aNums2);
        }

        for (int i = 0; i < nums1.length; i++) {
            int numi = nums1[i];
            result[i] = -1;
            //找到 nums1 中每个元素在 nums2 中的下一个比其大的值，（下一个开始找）
            for (int j = nums2list.indexOf(numi) + 1; j < nums2list.size(); j++) {
                if (nums2list.get(j) > numi){
                    result[i] = nums2list.get(j);
                    break;
                }
            }
        }
        return result;
    }


    /**
     * 反转一个单链表。
     输入: 1->2->3->4->5->NULL
     输出: 5->4->3->2->1->NULL
     * @param head
     * @return
     */
//    public ListNode reverseList(ListNode head) {
//        // 思路，递归可以吗？
////        while
//
//    }


}
