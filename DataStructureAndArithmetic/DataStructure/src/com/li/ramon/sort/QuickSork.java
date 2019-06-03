package com.li.ramon.sort;
/**
 * 快速排序
 * @author limeng
 *
 */
public class QuickSork {
	public static void main(String args[]){
		int[] a = {1, 3, 3, 4, 5,20,7};
		quickSort(a, 0, a.length - 1);
		for (int i = 0; i < a.length; i++) 
			System.out.println(a[i]);
	}
	// 获取关键字的位置
	private static int getMiddle(int arr[], int low, int high) {
		int temp = arr[low];// 数组的第一个作为中轴
		while (low < high) {
			while (low < high && arr[high] >= temp) {
				high--;
			}
			arr[low] = arr[high];// 比中轴小的移动到低端
			
			while (low < high && arr[low] < temp) {
				low ++;
			}
			arr[high] = arr[low]; // 比中轴大的移动到右边
		}
		arr[low] = temp; // 中轴放到它的位置上
		return low;
	}
	
	private static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int middle = getMiddle(arr, low, high);
			// 递归排列低位
			quickSort(arr, low, middle -1);
			quickSort(arr, middle + 1, high);
		}
	}
}
