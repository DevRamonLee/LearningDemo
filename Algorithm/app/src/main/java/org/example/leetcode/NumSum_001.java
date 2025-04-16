/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:47:12
 * @LastEditTime: 2025-03-31 11:46:50
 * @FilePath: /Algorithm/app/src/main/java/org/example/leetcode/NumSum_001.java
 * @Description: 
 */
package org.example.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/8 21:53
 */
public class NumSum_001 {
    public static void main(String[] args) {
        int[] nums = {3, 3};
        int[] result = twoSum2(nums, 6);
        print(result);
    }

    /**
     * 暴力破解法：双层 for 循环，时间复杂度未 O(n2)
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
         }
        return new int[] {0};
    }

    /**
     * 使用 Map，循环一次找到答案
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            if (map.containsKey(target - nums[i])) {
                return new int[] {i, map.get(target - nums[i])};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[] {0};
    }

    private static void print(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}