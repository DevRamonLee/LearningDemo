/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:48:48
 * @LastEditTime: 2025-03-31 12:03:38
 * @FilePath: /Algorithm/app/src/main/java/org/example/common/Permutation.java
 * @Description: 
 */
package org.example.common;

import java.util.Arrays;

/**
 * 
 * @author meng.li
 *	字符串全排列问题
 */

public class Permutation {
	private void swap(int[] chList, int pos1, int pos2) {
	    if (pos1 != pos2)
	    {
	        int tmp = chList[pos1]; 
	        chList[pos1] = chList[pos2];
	        chList[pos2] = tmp;
	    }
	}

	//将字符串[begin, end]区间的子串全排列
	public void permutation(int[] chList, int begin, int end)
	{
	    if (begin == end)//就剩一个字符了，不需要排列了，直接输出当前的结果
	    {
	        System.out.println(Arrays.toString(chList));
	    }

	    for (int i = begin; i < end; i++)
	    {
	    	swap(chList, begin, i); //把第i个字符换到begin位置，将begin+1位置看作新的子串开始
	    	permutation(chList, begin + 1, end); //求解子问题
	        swap(chList, begin, i); //在挑选下一个固定字符之前，需要换回来
	    }
	}
}
