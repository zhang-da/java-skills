package com.da.learn.leetcode;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SingleNumber {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = singleNumber3(new int[]{2, 2, 1, 1, 3, 3, 8, 5, 8, 5, 10});
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
    }

    private static int singleNumber1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int k : nums) {
            int c = map.getOrDefault(k, 0);
            map.put(k, c + 1);
        }
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int s = e.getKey();
            int t = e.getValue();
            if (t == 1) {
                return s;
            }
        }
        return -1;
    }

    private static int singleNumber2(int[] nums) {
        Arrays.sort(nums);  // 排序数组
        for (int i = 0; i < nums.length - 1; i += 2) {
            // 找到不相等的一组，直接返回
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        // 如果没有找到不相等的一组数据，直接返回数组的最后一个数字
        return nums[nums.length - 1];
    }

    /**
     * O(n)
     * @param nums
     * @return
     */
    private static int singleNumber3(int[] nums) {
        int num = nums[0];
        for(int i=1;i<nums.length;i++){
            num = num ^ nums[i];
        }
        return num;
    }
}

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * <p>
 * 说明：
 * <p>
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,2,1]
 * 输出: 1
 * 示例 2:
 * <p>
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */
