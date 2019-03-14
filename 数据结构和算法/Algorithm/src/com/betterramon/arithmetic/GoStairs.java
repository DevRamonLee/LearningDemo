package com.betterramon.arithmetic;

/**
 * @author limeng
 * 有 100 阶楼梯，每次只能走 1 步或者 走 2 步，有多少种走法
 * 分析： 
 * 假如有 n 阶台阶，第 n 阶向前退 1步，则要求 n -1 阶台阶走法
 * 第 n 阶向前退 2 步，则要求 n -2 阶台阶的走法
 * 所以 f(n) = f(n-1) + f(n-2)
 *
 */
public class GoStairs {
	// 递归算法实现
	public static int goStairs(int n) {
		if(n ==1) {
			return 1;
		} else if(n == 2) {
			return 2;
		} else {
			return goStairs(n -1) + goStairs(n -2);
		}
	}
	
    // 非递归算法实现 
	// https://blog.csdn.net/mlkiller/article/details/38422245
	public static int goStairs2(int n) {
		int a0 = 1;
		int a1 = 1;
		int a2 = 1;
		for (int i = 2; i <= n; i++ ) {
			a2 = a0 + a1;
			a0 = a1;
			a1 = a2;
		}
		return a2;
	}
}
