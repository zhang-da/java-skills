package com.da.learn.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = majorityElement3(new int[]{2,2,1,1,1,2,2});
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
    }

    private static int majorityElement1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            Integer value = map.get(num);
            if (value != null) {
                value += 1;
                if (Double.valueOf(value) > nums.length / 2.0) {
                    return num;
                }
                map.put(num, value);
            } else {
                map.put(num, 1);
            }
        }
        return -1;
    }

    private static int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        if(nums.length==0)
            return 0;
        if(nums.length==1)
            return nums[0];
        return nums[nums.length/2 ];
    }

    /**
     * 每找出两个不同的element，就成对删除即count--，最终剩下的一定就是所求的。时间复杂度：O(n) 空间复杂度为O(1)
     */
    private static int majorityElement3(int[] nums) {
        int index=0;
        int cnt=0;
        for(int num:nums){
            if(cnt==0){
                index=num;
                cnt=1;
            }else if(index==num){
                cnt++;
            }else{
                cnt--;
            }
        }
        return index;
    }
}

/**
 * 给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在众数。
 *
 * 示例 1:
 *
 * 输入: [3,2,3]
 * 输出: 3
 * 示例 2:
 *
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 */
