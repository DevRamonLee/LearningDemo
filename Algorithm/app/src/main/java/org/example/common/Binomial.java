/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:48:48
 * @LastEditTime: 2025-03-31 12:03:12
 * @FilePath: /Algorithm/app/src/main/java/org/example/common/Binomial.java
 * @Description: 
 */
package org.example.common;

import org.example.common.model.Item;

/**
 * 例: n 次二项式的展开公式
 * @author meng.li
 *
 */
public class Binomial {
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
