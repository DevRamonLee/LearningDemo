#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * @brief 暴力破解法
 * 
 * @param array 
 * @param length 
 * @return int  
 */
int get_repeated_num(int array[], int length) {
    if (array == NULL || length <= 0) {
        return -1;
    }
    for (int i = 0; i < length; i++) {
        if (array[i] < 0 || array[i] > length -1) {
            return -1;
        }
    }
    for (int i = 0; i < length; i++) {
        int target = array[i];
        for (int j = i +1; j < length; j++) {
            if (target == array[j]) {
                return target;
            }
        }
    }
    return -1;
}

/**
 * @brief 交换下标法
 * 
 * @param array 
 * @param length 
 * @return int 
 */
int get_repeated_num2(int array[], int length) {
    if (array == NULL || length <= 0) {
        return -1;
    }
    for (int i = 0; i < length; i++) {
        if (array[i] < 0 || array[i] > length -1) {
            return -1;
        }
    }
    for (int i = 0; i < length; i++) {
        while (array[i] != i) {
            if (array[i] == array[array[i]]) {
                return array[i];
            }
            int temp = array[i];
            array[i] = array[array[i]];
            array[temp] = temp;
        }
    }
    return -1;
}

/**
 * @brief 不能修改原来的数组
 * 
 * @param array 
 * @param length 
 * @return int 
 */
int get_repeated_num3(int array[], int length) {
    if (array == NULL || length <= 0) {
        return -1;
    }
    for (int i = 0; i < length; i++) {
        if (array[i] < 0 || array[i] > length -1) {
            return -1;
        }
    }
    int *temp = (int*)malloc(length * sizeof(int));
    if(temp == NULL) {
        printf("malloc fail");
        return -1;
    }
    memset(temp, -1, length * sizeof(int));
    for (int i = 0; i < length; i++) {
        if(temp[array[i]] == -1) {
            temp[array[i]] = array[i];
        } else {
            free(temp);
            return array[i];
        }
    }
    free(temp);
    return -1;
}

/**
 * @brief 计算 start 到 end 在数组中出现的次数
 * 
 * @param array 
 * @param length 
 * @param start 
 * @param end 
 * @return int 
 */
int count_range(int array[], int length, int start, int end) {
    int count = 0;
    for (int i = 0; i < length; i++) {
        if((array[i] >= start) && array[i] <= end) {
            count++;
        }
    }
    return count;
}
/**
 * @brief 不改变原数组，不创建辅助空间，测试数据范围为 1 - n
 * 
 * @param array 
 * @param length 
 * @return int 
 */
int get_repeated_num4(int array[], int length) {
    if (array == NULL || length <= 0) {
        return -1;
    }
    for (int i = 0; i < length; i++) {
        if (array[i] < 1 || array[i] > length) {
            return -1;
        }
    }
    int start = 1;
    int end = length - 1;
    while (start <= end) {
        int middle = (start + end) >> 1;
        int count = count_range(array, length, start, middle);
        if (start == end) {
            if (count > 1) {
             return start;
            } else {
                break;
            }
        }
        if (count > (middle - start + 1)) {
            end = middle;
        } else {
            start = middle + 1;
        }
    }
    return -1;
}

void run_tests() {
    int test_cases[][8] = {    // 用二维数组存储测试用例
        {2, 3, 1, 3, 2, 5, 3, -1},   // 测试用例 1：有效范围 0 ~ 6
        {4, 1, 2, 3, 4, 5, 6, -1},   // 测试用例 2：无重复数字，范围 0 ~ 6
        {0, 0, 0, 0, 0, -1, -1, -1}, // 测试用例 3：所有数字相同 (0)，范围 0 ~ 4
        {},                          // 测试用例 4：空数组
        {4, 3, 2, 1, 0, 5, 5, 6}     // 测试用例 5：有效范围 0 ~ 7
    };
    
    int test_lengths[] = {7, 7, 5, 0, 8};  // 每个测试用例的有效长度
    int num_tests = sizeof(test_lengths) / sizeof(test_lengths[0]);

    for (int i = 0; i < num_tests; i++) {
        int result = get_repeated_num4(test_cases[i], test_lengths[i]);
        
        printf("测试用例 %d：", i + 1);
        if (result != -1) {
            printf("找到的重复数字是: %d\n", result);
        } else {
            printf("未找到重复数字。\n");
        }
    }
}

int main() {
    run_tests();
    return 0;
}