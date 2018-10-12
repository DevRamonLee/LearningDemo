package com.betterramon.arithmetic;

/**
 * 例： 警察抓了 A、B、C、D 四名罪犯，其中一名是小偷，审讯的时候：
	A说：“我不是小偷。” x !=0
	B说：“C 是小偷。” x = 2
	C说：“小偷肯定是 D。” x = 3 
	D说：“C 是在冤枉人。” x != 3
	现在已经知道四个人中三个人说的是真话，一个人说了假话，请判断一下到底谁是小偷？
 * @author meng.li
 *
 */
public class WhoIsThief {
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
}
