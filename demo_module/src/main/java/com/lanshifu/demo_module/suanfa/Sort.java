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
        insertSort(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
        quick(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
    }

    /**
     *
     * 冒泡
     *
     * @param nums
     */
    public int[] bubbleSort(int nums[]) {

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                //比较前后两个数，前面大就交换位置
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        System.out.println("冒泡排序结果：");
        for (int num : nums) {
            System.out.println(num);
        }

        return nums;
    }


    /**
     * 选择排序：
     * 选出最小的一个数与第一个位置的数交换；
     * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
     * 4 3 2 1
     * 1 3 2 4
     * 1 2 3 4
     *
     * @param nums
     */
    public void selectSort(int nums[]) {
        for (int i = 0; i < nums.length; i++) {
            //第一个作为参照，最小
            int min = nums[i];

            int secondMinIndex = i + 1;
            //后面那些数取最少的数，
            for (int j = i + 1; j < nums.length; j++) {
                if (j < nums.length - 1 && nums[j + 1] < nums[j]) {
                    secondMinIndex = j + 1;
                }
            }
            //跟第一个比较，如果小就替换
            if (secondMinIndex < min) {
                int temp = nums[secondMinIndex];
                nums[secondMinIndex] = nums[i];
                nums[i] = temp;
            }
        }

        System.out.println("选择排序结果：");
        for (int num : nums) {
            System.out.println(num);
        }

    }

    /**
     * 插入排序
     *
     *  从第一个元素开始，该元素可以认为已经被排序
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 将新元素插入到该位置中
     * 重复步骤2
     * @param nums
     */
    public void insertSort(int[] nums){

        // 4 3 2 1
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            //前面已经排好序了，假如temp比前面的值小，则将前面的值后移
            for (j = i; j > 0 && temp < nums[j -1]; j--) {
                nums[j] = nums[j-1];
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
     * @param numbers 带排序数组
     */
    public static void quick(int[] numbers)
    {
        if(numbers.length > 0)   //查看数组是否为空
        {
            quickSort(numbers, 0, numbers.length-1);
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
     * @param low   开始位置
     * @param high  结束位置
     * @return  中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low,int high)
    {
        int temp = numbers[low]; //数组的第一个作为中轴
        while(low < high)
        {
            while(low < high && numbers[high] >= temp)
            {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while(low < high && numbers[low] <= temp)
            {
                low++;
            }
            numbers[high] = numbers[low] ; //比中轴大的记录移到高端
        }
        numbers[low] = temp ; //中轴记录到尾
        return low ; // 返回中轴的位置
    }

    /**
     *
     * @param numbers 带排序数组
     * @param low  开始位置
     * @param high 结束位置
     */
    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }

    }

}
