/*
 * @Author: Ramon
 * @Date: 2025-05-13 11:39:17
 * @LastEditTime: 2025-05-13 11:54:58
 * @FilePath: /SwordOffer/src/14rearrange_odd_and_even.c
 * @Description:
 */
#include <stdio.h>
#include <stdlib.h>

int is_even(int n) {
    return !(n % 2);
}

void rearrange_odd_and_even(int arr[], int length, int (*func)(int))
{
	if (arr == NULL || length <= 0)
		return;
	int start = 0;
	int end = length - 1;
	while (start < end)
	{
		// 如果前面是奇数，跳过
		while (start < end && !func(arr[start])) {
			start++;
		}
		// 如果后面是偶数，跳过
		while (start < end && func(arr[end])) {
			end--;
		}
		// 前是偶数，后是奇数，交换
		int temp = arr[start];
		arr[start] = arr[end];
		arr[end] = temp;
		start++;
		end--;
	}
}


// 打印数组的辅助函数
void print_array(int arr[], int length)
{
	for (int i = 0; i < length; i++) {
		printf("%d ", arr[i]);
	}
	printf("\n");
}

int main()
{
	// 测试用例 1：混合奇偶
	int arr1[] = {1, 2, 3, 4, 5, 6};
	printf("Test 1 - Mixed:       ");
	rearrange_odd_and_even(arr1, 6, is_even);
	print_array(arr1, 6);

	// 测试用例 2：全奇数
	int arr2[] = {1, 3, 5, 7};
	printf("Test 2 - All Odd:     ");
	rearrange_odd_and_even(arr2, 4, is_even);
	print_array(arr2, 4);

	// 测试用例 3：全偶数
	int arr3[] = {2, 4, 6, 8};
	printf("Test 3 - All Even:    ");
	rearrange_odd_and_even(arr3, 4, is_even);
	print_array(arr3, 4);

	// 测试用例 4：交替排列
	int arr4[] = {2, 1, 4, 3, 6, 5};
	printf("Test 4 - Alternating: ");
	rearrange_odd_and_even(arr4, 6,is_even);
	print_array(arr4, 6);

	// 测试用例 5：包含负数
	int arr5[] = {-3, -2, -1, 0, 1, 2};
	printf("Test 5 - With Negatives: ");
	rearrange_odd_and_even(arr5, 6,is_even);
	print_array(arr5, 6);

	// 测试用例 6：空数组
	int arr6[] = {};
	printf("Test 6 - Empty:       ");
	rearrange_odd_and_even(arr6, 0,is_even);
	print_array(arr6, 0);

	// 测试用例 7：只有一个元素（奇数）
	int arr7[] = {7};
	printf("Test 7 - Single Odd:  ");
	rearrange_odd_and_even(arr7, 1,is_even);
	print_array(arr7, 1);

	// 测试用例 8：只有一个元素（偶数）
	int arr8[] = {8};
	printf("Test 8 - Single Even: ");
	rearrange_odd_and_even(arr8, 1,is_even);
	print_array(arr8, 1);

	return 0;
}
