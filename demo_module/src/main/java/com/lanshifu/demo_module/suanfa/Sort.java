package com.lanshifu.demo_module.suanfa;

/**
 * 插入排序
 * 希尔排序
 * 选择排序
 * 冒泡排序
 * 计数排序
 * 基数排序
 * 归并排序
 * 快速排序
 * 双向扫描的快速排序
 * 堆排序
 */
public class Sort {


    public void test() {
        System.out.println("test");

        bubbleSort(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
        selectSort(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
//        insertSort(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
//        quick(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});

        System.out.println("二分查找 ");
        int find = findBy2(new int[]{3,3,3,3,3,3,5,6,7,8,9},3);
        System.out.println(find);

        int circleIndex = findCircleIndex(new int[]{4, 5, 6, 1, 2, 3}, 2);
        System.out.println("循环链表查找：" + circleIndex);
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
    public int[] bubbleSort(int nums[]) {
        //4321  3421  3241  3214  第一趟
        //3214  2314  2134        第二趟
        //2134  1234              第三趟
        for (int i = 0; i < nums.length -1; i++) {  //只需比较 length -1 次
            // nums.length - i -1 ,要跟后面比较,所以要减一
            for (int j = 0; j < nums.length - i -1; j++) {
                if (nums[j] > nums[j+1]){
                    //比后面大就交换位置
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j] = temp;
                }
            }
        }
        System.out.println("冒泡排序结果：");
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]+" ");
        }
        return nums;
    }


    /**
     * 选择排序：
     * 选出最小的一个数与第一个位置的数交换；
     * 然后在剩下的数当中再找最小的与第二个位置的数交换,如此循环到倒数第二个数和最后一个数比较为止。
     * 4 3 2 1
     * 1 3 2 4
     * 1 2 3 4
     *
     * @param nums
     */
    public void selectSort(int nums[]) {
        for (int i = 0; i < nums.length; i++) {

            int min = nums[i]; //假设第一个最小
            int minIndex = i;  //记录最小的下标,方便交换
            for (int j = i +1; j < nums.length; j++) {
                //对比后面选出最小
                if (nums[j] < min){
                    min = nums[j];
                    minIndex = j;
                }
            }
            //选出了最小,与第一个位置交换
            int temp = nums[i];
            nums[i] = min;
            nums[minIndex] = temp;
        }


        System.out.println("选择排序结果：");
        for (int num : nums) {
            System.out.println(num);
        }

    }

    /**
     * 插入排序
     * <p>
     * 从第一个元素开始,该元素可以认为已经被排序
     * 取出下一个元素,在已经排序的元素序列中从后向前扫描
     * 如果该元素（已排序）大于新元素,将该元素移到下一位置
     * 重复步骤3,直到找到已排序的元素小于或者等于新元素的位置
     * 将新元素插入到该位置中
     * 重复步骤2
     *
     * @param nums
     */
    public void insertSort(int[] nums) {

        // 4 3 2 1
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            //前面已经排好序了,假如temp比前面的值小,则将前面的值后移
            for (j = i; j > 0 && temp < nums[j - 1]; j--) {
                nums[j] = nums[j - 1];
            }
            //把temp放到移动的位置上
            nums[j] = temp;
        }

        System.out.println("插入排序：");
        for (int num : nums) {
            System.out.println(num);
        }

    }

    /**
     * 快速排序
     *
     * @param numbers 带排序数组
     */
    public static void quick(int[] numbers) {
        if (numbers.length > 0)   //查看数组是否为空
        {
            quickSort(numbers, 0, numbers.length - 1);
        }
        System.out.println("快速排序：");
        for (int num : numbers) {
            System.out.println(num);
        }
    }

    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers 带查找数组
     * @param low     开始位置
     * @param high    结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; //数组的第一个作为中轴
        while (low < high) {
            while (low < high && numbers[high] >= temp) {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端

            while (low < high && numbers[low] <= temp) {
                low++;
            }
            numbers[high] = numbers[low]; //比中轴大的记录移到高端
        }
        numbers[low] = temp; //中轴记录到尾
        return low; // 返回中轴的位置
    }

    /**
     * @param numbers 带排序数组
     * @param low     开始位置
     * @param high    结束位置
     */
    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }

    }

    /**
     * 二分查找
     * @param numbers  有序数组
     * @param findNumber 查找的数
     * @return
     */
    public int findBy2(int[] numbers,int findNumber){
        int start = 0;
        int end = numbers.length;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (findNumber == numbers[mid]) {
                //如果是要求找到第一个 给定的值,比如数组里面有两个8,要找到第一个8
                if (mid == 0){  //如果是第一个,那肯定是
                    return mid;
                }else if (numbers[mid -1] != findNumber){
                    //中间的前一个不是,那么也说明是第一个
                    return mid;
                }
                //如果不是第一个,说明第一个在左边
                end = mid -1;
            } else if (findNumber > numbers[mid]) {
                start = mid + 1;
                System.out.println("start="+start);
                //大于中间,在右边
            }else {
                //小于中间,在左边
                end = mid -1;
                System.out.println("end="+end);
            }
        }
        //没找到
        return -1;
    }

    //二分法,递归方式
    public int findBy2(int[] numbers,int findNumber,int start,int end){
        while (start <= end) {
            int mid = (start + end) / 2;
            if (findNumber == numbers[mid]) {
                //如果是要求找到第一个 给定的值,比如数组里面有两个8,要找到第一个8
                if (mid == 0){  //如果是第一个,那肯定是
                    return mid;
                }else if (numbers[mid -1] != findNumber){
                    //中间的前一个不是,那么也说明是第一个
                    return mid;
                }
                //如果不是第一个,说明第一个在左边
                end = mid -1;
            } else if (findNumber > numbers[mid]) {
                start = mid + 1;
                return findBy2(numbers,findNumber,start,end);
                //大于中间,在右边
            }else {
                //小于中间,在左边
                end = mid -1;
                return findBy2(numbers,findNumber,start,end);
            }
        }
        //没找到
        return -1;
    }

    /**
     如果有序数组是一个循环有序数组,比如 4,5,6,1,2,3,针对这种情况,
     如何实现一个求“值等于给定值”的二分查找算法呢
     */
    public int findCircleIndex(int[] numbers, int findNumber){
        //找到临界点,分成两个有序数组,4,5,6 和 1,2,3，分别二分查找
        int index = 0;
        for (int i = 0; i < numbers.length-1; i++) {
            if (numbers[i+1] >numbers[i]){
                index = i+1;  //3
                break;
            }
        }
        int result = findBy2(numbers,findNumber,0,index);
        if (result != -1){
            return result;
        }
        return findBy2(numbers,findNumber,index +1,numbers.length);
        
    }


}
