/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:50:50
 * @LastEditTime: 2025-03-31 12:00:59
 * @FilePath: /Algorithm/app/src/main/java/org/example/datastructure/sort/BubbleSort.java
 * @Description: 
 */
package org.example.datastructure.sort;
/**
 * 冒泡排序算法
 * @author limeng
 *
 */
public class BubbleSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {23, 122, 2, 5, 9, 10};
		bubbleSort(a);
		//bubbleSortFine(a);
		for (int i = 0; i < a.length; i++) 
			System.out.println(a[i]);
	}
	
	// 冒泡排序一般写法
	public static void bubbleSort(int[] arr) {
		for (int i = 0; i< arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j +1]) {
					// 利用非运算交换
					arr[j] = arr[j] ^ arr[j + 1];
					arr[j + 1] = arr[j] ^ arr[j + 1];
					arr[j] = arr[j + 1] ^ arr[j];
				}
			}
		}
	}
	
	// 冒泡排序的优化，如果在一次遍历交换中没有进行交换，则证明序列已经是有序的则跳出循环
	public static void bubbleSortFine(int[] arr) {
		boolean flag = true;
		int k = arr.length;
		while (flag) {
			flag = false;
			for (int i = 1; i < k; i++) {
				if (arr[i-1] > arr[i]) {
					// 利用减法
					arr[i-1] = arr[i] - arr[i-1];
					arr[i] = arr[i] - arr[i - 1];
					arr[i - 1] = arr[i -1] + arr[i];
					flag = true;
				}
			}
			k--;
		}
	}
}
