/*
 * @Author: Ramon
 * @Date: 2025-05-07 10:29:44
 * @LastEditTime: 2025-05-07 10:59:50
 * @FilePath: /SwordOffer/src/10count_1_nums.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

// 此解法当输入负数时会产生死循环，因为负数右移后会在符号位重新补 1
int count_1_nums(int n)
{
    int count = 0;
    while (n)
    {
        if (n & 1) {
            count++;
        }
        n = n >> 1;
    }
    return count;
}

int count_1_nums_2(int n)
{
    int count = 0;
    unsigned int flag = 1;
    while(flag)
    {
        if (n & flag) {
            count ++;
        }
        flag = flag << 1;
    }
    return count;
}

int count_1_nums_3(int n)
{
    int count = 0;
    while (n)
    {
        ++count;
        n = n & (n - 1);
    }
    return count;
}

int main(int argc, char const *argv[])
{
    printf("total 1 is %d", count_1_nums_3(-1));
    return 0;
}
