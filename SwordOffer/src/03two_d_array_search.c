/*
 * @Author: Ramon
 * @Date: 2025-03-26 12:32:23
 * @LastEditTime: 2025-04-15 10:27:46
 * @FilePath: /SwordOffer/src/03two_d_array_search.c
 * @Description: 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int two_d_array_search(int **a, int columnCount, int rowCount, int target) {
    int i = columnCount - 1;
    int j = 0;
    if ( a == NULL) {
        return 0;
    }
    
    while(i >= 0 && j < rowCount) {
        if (a[j][i] == target) {
            return 1;
        } else if (a[j][i] > target) {
            i--;
        } else {
            j++;
        }
    }
    return 0;
}

void testMethod() {

    int rowCount = 4, columnCount = 4;
    
    // 动态分配二维数组
    int **a = (int **)malloc(rowCount * sizeof(int *));
    for (int i = 0; i < rowCount; i++) {
        a[i] = (int *)malloc(columnCount * sizeof(int));
    }

    // 初始化数组
    int values[4][4] = {
        {1, 2, 8, 9}, 
        {2, 4, 9, 12}, 
        {4, 7, 10, 13}, 
        {6, 8, 11, 15}
    };
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < columnCount; j++) {
            a[i][j] = values[i][j];
        }
    }

    int result = two_d_array_search(a, 4, 4, 7);
    if (result == 1) {
        printf("finded the number\n");
    } else {
        printf("not find the number\n");
    }
    // 释放内存
    for (int i = 0; i < rowCount; i++) {
        free(a[i]);
    }
    free(a);
}

int main() {
    testMethod();
    return 0;
}