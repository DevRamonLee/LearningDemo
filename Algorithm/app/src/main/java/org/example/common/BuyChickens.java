/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:48:48
 * @LastEditTime: 2025-03-31 12:03:17
 * @FilePath: /Algorithm/app/src/main/java/org/example/common/BuyChickens.java
 * @Description: 
 */
package org.example.common;
/**
 * 
 * @author meng.li
 *一百个钱买一百只鸡，是个典型的穷举法应用。
 *问题描述：每只大公鸡值 5 个钱，每只母鸡值 3 个钱，每 3 只小鸡值 1 个钱，现在有 100 个钱，想买 100 只鸡，问如何买？有多少种方法？
 */

public class BuyChickens {
	public void buyChickens() {
		for(int roosters = 1; roosters <= 20; roosters++) { //先买公鸡，100 钱最多可以买20只
			for(int hens = 1; hens <= 33; hens++){ //买母鸡，最多只能买 33只 
				int chicks = 100 - roosters - hens;//小鸡的数量用 100 减去 公鸡和母鸡的数量，且小鸡必须是 3 的倍数
				if(chicks % 3 == 0 
						&& ((5*roosters + 3 * hens + chicks/3) == 100)){//小鸡数量是不是 3 的倍数，不是的话直接结束当前循环
					System.out.println("买公鸡 " + roosters + " 只，母鸡 " + hens + " 只，小鸡 " + chicks +" 只");
				}
			}
		}
	}
	
	/**
	 * 附加题： 有鸡和兔在一个笼子中，数头共 50 个头，数脚共 120 只脚，问：鸡和兔分别有多少只？
	 */
	public void chickensAndRabbits() {
		for(int rabbits = 1; rabbits <= 40; rabbits ++) {
			int chickens = 50 - rabbits;
			if((rabbits * 4 + chickens * 2) == 120) {
				System.out.println("兔子有 " + rabbits + " 只 ，鸡有 "+ chickens + " 只");
			}
		}
	}
}
