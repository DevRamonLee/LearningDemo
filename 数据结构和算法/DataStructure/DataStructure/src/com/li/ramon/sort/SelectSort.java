package com.li.ramon.sort;
/**
 * 选择排序
 * 在未排序序列中找到最小元素，存放到排序序列的起始位置  
 * 再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。 
 * 以此类推，直到所有元素均排序完毕。 
 * @author limeng
 */
public class SelectSort {
	public static void main(String[] args) {
		int[] a = {231, 122, 2, 5, 9, 10};
		SelectSort(a);
		for (int i = 0; i < a.length; i++) 
			System.out.println(a[i]);
	}
	private static void SelectSort(int[] arr) {
		int size = arr.length;//数组长度
		int temp = 0; // 中间变量
		for (int i = 0; i < size; i++) {
			int k = i; //待确定的位置
			for (int j = size -1; j > i; j--) {
				if(arr[j] < arr[k]) {
					k = j;//找到最小数的下标
				}
			}
			// 交换两个数
			temp = arr[i];
			arr[i] = arr[k];
			arr[k] = temp;
		}
	}
}
