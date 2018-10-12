package com.betterramon.arithmetic;

import java.util.ArrayList;

import com.betterramon.model.Merchandise;

/**
 * 有一个背包，最多能承载重量为 C=150 的物品， 现在有 7 个物品（物品不能分割成任意大小）， 编号为 1~7，重量分别是
 * wi=[35、30、60、50、40、10、25]， 价值分别是 pi=[10、40、30、50、35、40、30]， 现在从这 7
 * 个物品中选择一个或多个装入背包， 要求在物品总重量不超过 C 的前提下，所装入的物品总价值最高。
 * 
 * @author meng.li
 * 
 */
public class KnapsackProblem {
	ArrayList<Merchandise> objs; // 可选问题列表
	int totalC;// 背包总承重

	public KnapsackProblem(ArrayList<Merchandise> objs, int totalC) {
		this.objs = objs;
		this.totalC = totalC;
	}
	
	/**
	 * 
	 * @param problem 背包问题定义：背包问题包括两个属性，一个是可选物品列表，另一个是背包总的承重量，
	 * @param ploy 选择哪一个策略
	 */
	public void greedyAlgo(KnapsackProblem problem, int ploy) {
		int idx;
		int ntc = 0;

		// Choosefunc 每次选最符合策略的那个物品，选后再检查是否超重
		while ((idx = choosefunc(objs, ploy)) != -1) {
			// 所选物品是否满足背包承重要求？
			if ((ntc + objs.get(idx).weight) <= totalC) {
				objs.get(idx).status = 1;
				ntc += objs.get(idx).weight;
				System.out.print((idx + 1) + " ");
			} else {
				// 超重了，不能选这个物品了，做个标记后重新选
				objs.get(idx).status = 2;
			}
		}
	}

	/**
	 * 贪心算法选择策略
	 * 
	 * @param objs 可选问题列表
	 * @param ploy 选择策略
	 * @return 符合当前策略的下标
	 */
	int choosefunc(ArrayList<Merchandise> objs, int ploy) {
		int index = -1; // -1表示背包容量已满
		switch (ploy) {
		case 0: // 贪婪策略1： 每次总选择价值最大的物品
			int maximumPrice = 0;
			for (int i = 0; i < objs.size(); i++) {
				if ((objs.get(i).status == 0) && (objs.get(i).price > maximumPrice)) {
					maximumPrice = objs.get(i).price;
					index = i;
				}
			}
			break;
		case 1://贪婪策略2: 每次选择重量最轻的物品
			int mininumWeight = Integer.MAX_VALUE;
			for (int i = 0; i < objs.size(); i++) {
				if ((objs.get(i).status == 0) && (objs.get(i).weight < mininumWeight)) {
					mininumWeight = objs.get(i).weight;
					index = i;
				}
			}
			break;
		case 2://贪婪策略3：每次选择价值密度最大的物品
			float maxmumDesityPrice = 0;
			for (int i = 0; i < objs.size(); i++) {
				if (objs.get(i).status == 0){
					//计算价值密度,这里注意数据类型，需要把整型转为float，否则会没有小数位
					float tempDesityPrice = (float)objs.get(i).price/objs.get(i).weight;
					if(tempDesityPrice > maxmumDesityPrice) {
						maxmumDesityPrice = tempDesityPrice;
						index = i;
					}
				}
			}
			break;
		default:
			break;
		}
		return index;
	}
}
