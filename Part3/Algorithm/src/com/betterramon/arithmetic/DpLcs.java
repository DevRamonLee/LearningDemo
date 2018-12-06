package com.betterramon.arithmetic;

/**
 * 
 * @author meng.li 动态规划法求最长公共子序列
 */
public class DpLcs {
   public int dPLcs(String s1, String s2, int s[][]) {
		char str1[] = s1.toCharArray();
		char str2[] = s2.toCharArray();
		for (int i = 1; i <= s1.length(); i++)
			s[i][0] = 0;
		for (int j = 1; j <= s2.length(); j++)
			s[0][j] = 0;
		for (int p = 1; p <= s1.length(); p++) {
			for (int q = 1; q <= s2.length(); q++) {
				if (str1[p-1] == str2[q-1]) {
					s[p][q] = s[p-1][q-1] + 1;
				} else {
					s[p][q] = Math.max(s[p - 1][q], s[p][q - 1]);//把上一阶段最大的值赋值给当前
				}
			}
		}
		return s[s1.length()][s2.length()];
	}
}
