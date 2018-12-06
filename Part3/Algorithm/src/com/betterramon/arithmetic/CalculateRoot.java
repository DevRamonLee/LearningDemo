package com.betterramon.arithmetic;
/**
 * 
 * @author meng.li
 * 使用迭代法求一个数的平方根
 */
public class CalculateRoot {
	private static final int LOOP_LIMIT = 100;
	/**
	 * 
	 * @param a 待求平方根的数
	 * @param eps 精度
	 * @return
	 */
	public double clRoot(double a, double eps) {
		double xi = a/2.0;//初始值用 a 的一半，常用选择
		double xt;
		int count = 0;
		do{
			xt = xi;
			xi = (xt + (a/xt))/2.0;
			count++; //用于检查是否收敛的计数器
			if(count >= LOOP_LIMIT){
				return -1;//不收敛返回失败
			}
		}while(Math.abs(xi - xt) > eps);//绝对值小于精度则循环结束
		return xi;
	}
}
