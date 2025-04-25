/*
 * @Author: Ramon
 * @Date: 2025-04-25 13:34:08
 * @LastEditTime: 2025-04-25 14:54:37
 * @FilePath: /SwordOffer/src/08rotated_array.c
 * @Description:
 */
#include <stdio.h>
#include <stdlib.h>

int findMinInOrder(int array[], int start, int end) {
    int min = array[start];
    for (int i = start + 1; i <= end; ++i) {
        if (array[i] < min) {
            min = array[i];
        }
    }
    return min;
}

int findMinValue(int array[], int length) {
    if ((array == NULL) || length <= 0) {
        return -1;
    }
    int index1 = 0;
    int index2 = length - 1;
    int indexMid = index1;
    while (array[index1] >= array[index2]) {
        if ((index2 - index1) == 1) {
            indexMid = index2;
            break;
        }
        indexMid = (index1 + index2) / 2;
        // 无法判断最小值在哪边，只能顺序查找，比如输入 {1, 1, 1, 0, 1};
        if (array[index1] == array[indexMid] && array[indexMid] == array[index2]) {
            return findMinInOrder(array, index1, index2);
        }

        if (array[indexMid] >= array[index1]) {
            index1 = indexMid;
        } else if (array[indexMid] <= array[index2]) {
            index2 = indexMid;
        }
    }
    return array[indexMid];
}

void test(int array[], int length, const char* name) {
    int min = findMinValue(array, length);
    printf("%s -> Min value: %d\n", name, min);
}

int main() {
    int array1[] = {3, 4, 5, 1, 2};         // 普通旋转
    int array2[] = {1, 1, 1, 0, 1};         // 有重复
    int array3[] = {2, 2, 2, 2, 2};         // 全重复
    int array4[] = {10, 1, 10, 10, 10};     // 典型需要顺序查找的情况
    int array5[] = {1, 2, 3, 4, 5};         // 非旋转有序数组（最小值在开头）

    test(array1, sizeof(array1)/sizeof(int), "Test1: {3,4,5,1,2}");
    test(array2, sizeof(array2)/sizeof(int), "Test2: {1,1,1,0,1}");
    test(array3, sizeof(array3)/sizeof(int), "Test3: {2,2,2,2,2}");
    test(array4, sizeof(array4)/sizeof(int), "Test4: {10,1,10,10,10}");
    test(array5, sizeof(array5)/sizeof(int), "Test5: {1,2,3,4,5}");

    return 0;
}
