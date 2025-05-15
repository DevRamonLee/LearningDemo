/*
 * @Author: Ramon
 * @Date: 2025-05-08 09:45:24
 * @LastEditTime: 2025-05-08 10:37:25
 * @FilePath: /SwordOffer/src/11double_pow.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

double double_pow(double base, int exponent)
{
    // 处理 0 的 0 次方
    if (base == 0.0 && exponent == 0) {
        // 根据实际需求返回结果，可以是 1 或 NaN
        return NAN;
    }
    // 0 不能做除数
    if (base == 0.0 && exponent < 0) {
        return INFINITY;
    }
    double result = 1;
    int minus_flag = 0;

    if (base == 0 || base == 1) {
        return base;
    }
    if (exponent == 0) {
        return 1;
    }
    // 处理 INT_MIN 的特殊情况，负号操作无法覆盖最小值，会导致错误。
    unsigned int abs_exp;
    if (exponent < 0) {
        minus_flag = 1;
        // 用 long 避免 -INT_MIN 溢出
        abs_exp = (unsigned int)(-(long)exponent);
    } else {
        abs_exp = (unsigned int)exponent;
    }
    while (abs_exp > 0) {
        if (abs_exp % 2 == 1) {
            result *= base;
        }
        base *= base;
        abs_exp /= 2;
    }
    if (minus_flag) {
        return 1 / result;
    }
    return result;
}

int main(int argc, char const *argv[])
{
    printf("result is %f", double_pow(3, -3));
    return 0;
}
