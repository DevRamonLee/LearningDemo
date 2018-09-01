package com.betterramon.arithmetic;

import java.util.ArrayList;
import java.util.List;

import com.betterramon.model.Item;

public class DataModel {

	// 抽象问题数字化
	public void isThief() {
		for (int x = 0; x < 4; x++) {
			int dis_a = (x != 0) ? 1 : 0;// A is not thief
			int dis_b = (x == 2) ? 1 : 0;// C is thief
			int dis_c = (x == 3) ? 1 : 0;// D is thief
			int dis_d = 1 - dis_c; // C is lying
			if ((dis_a + dis_b + dis_c + dis_d) == 3) {
				char thief = (char) ('A' + x);
				System.out.println(thief + " is thief");
				break;
			}

		}
	}

	
	// 数学公式建模
	public Item[] calculateModulus(int n) {
		Item[] items = new Item[n+1];

		if (n == 0) {
			items[0] = new Item();
			items[0].c = 1;
			items[0].am = 0;
			items[0].bm = 0;
			return items;
		}
		// 从第1阶开始递推到第n阶
		for (int i = 1; i <= n; i++) {
			int nc = i + 1; // 每一阶的项数,按照杨辉三角，n + 1
			// 末项 系数为 1，am 和 bm ，一个是从 n 到 0 递减，一个是从 0 到 n 递增。
			items[nc -1] = new Item();
			items[nc - 1].c = 1;// 末项
			items[nc - 1].am = 0;
			items[nc - 1].bm = i;
			// 倒着递推第2项到第n-1项的值，实际下标范围是[1, nc-2],不需要额外的存储空间转存items数组
			for (int j = nc - 2; j > 0; j--) {
				int c = items[j].c + items[j - 1].c;
				items[j].c = c;
				items[j].am = i - j;
				items[j].bm = j;
			}
			items[0] = new Item();
			items[0].c = 1;// 首项
			items[0].am = i;
			items[0].bm = 0;
		}
		return items;
	}
}
