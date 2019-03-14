package com.li.ramon.list;

import java.util.Random;

/**
 * 约瑟夫环问题
 * 
 * @author limeng
 * 
 */
public class YueSeFu {

	public static void main(String[] args) {
		yuesefu(10, 3, 2);
	}

	public static void yuesefu(int totalNum, int countNum, int startNum) {
		// 初始化人数
		CircularLinkedList<Integer> yueSeFu = new CircularLinkedList<Integer>();
		for (int i = 1; i <= totalNum; i++) {
			yueSeFu.add(i);
		}
		// 从下标为 k 开始计数
		int k = startNum - 1;
		while (yueSeFu.size > 0) {
			// 第m人的索引位置 ,减一是因为是从 第 k 个人就开始数
			k = (k + countNum) % (yueSeFu.size) - 1;
			// 数到队尾的时候，k 等于 -1
			if (k < 0) {
				System.out.println("编号为" + yueSeFu.get(yueSeFu.size - 1)
						+ " 的人出列");
				yueSeFu.delete(yueSeFu.size - 1);
				k = 0;
			} else {
				System.out.println("编号为" + yueSeFu.get(k) + " 的人出列");
				yueSeFu.delete(k);
			}
		}
	}
}
