/*
 * @Author: Ramon
 * @Date: 2025-05-09 09:50:02
 * @LastEditTime: 2025-05-09 11:33:08
 * @FilePath: /SwordOffer/src/12print_numbers.c
 * @Description:面试题 12：打印 1 到最大的 n 位数
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 此种方法未考虑大数的情况
void print_numbers(int n)
{
    if (n <= 0) {
        return;
    }
    int max = 1;
    for (int i = 0; i < n; i++) {
        max = max * 10;
    }
    for (int j = 1; j < max; j++) {
        printf("%d ", j);
    }
}
// ---------------------------
// 采用字符串模拟数字递增的方式
int increase_number(char *num)
{
    int is_overflow = 0;
    int len = strlen(num);
    int take_over = 0;
    for (int i = len - 1; i >= 0; i--)
    {
        int sum = num[i] - '0' + take_over;
        if (i == len - 1)
        {
            sum++;
        }
        if (sum >= 10)
        {
            if (i == 0)
            {
                is_overflow = 1;
            }
            else
            {
                sum -= 10;
                take_over = 1;
                num[i] = '0' + sum;
            }
        }
        else
        {
            num[i] = '0' + sum;
            break;
        }
    }
    return is_overflow;
}

void print_num(char *num)
{
    int is_begin_0 = 1;
    int len = strlen(num);
    for (int i = 0; i < len; i++) {
        if (is_begin_0 && num[i] != '0')
        {
            is_begin_0 = 0;
        }
        if (!is_begin_0)
        {
            printf("%c", num[i]);
        }
    }
    printf("\n");
}

void print_numbers2(int n)
{
	if (n <= 0) {
		return;
	}
	char *number = (char *)malloc((n + 1) * sizeof(char));
	if (number == NULL) {
	    // malloc failed
	    return;
	}
	memset(number, '0', n);
	number[n] = '\0';
	while(!increase_number(number)) {
		print_num(number);
	}
    free(number);
}

// ----------------
// 使用递归全排列的方式

void print_num_recursively(char *num, int length, int index)
{
    if (index == length - 1)
    {
        print_num(num);
        return;
    }
    for (int i = 0; i < 10; i++) {
        num[index + 1] = i + '0';
        print_num_recursively(num, length, index + 1);
    }
}

void print_numbers3(int n)
{
    if (n <= 0) {
		return;
	}
	char *number = (char *)malloc((n + 1) * sizeof(char));
	if (number == NULL) {
	    // malloc failed
	    return;
	}
	memset(number, '0', n);
	number[n] = '\0';
    for (int i = 0; i < 10; i++)
    {
        number[0] = i + '0';
        print_num_recursively(number, n, 0);
    }
    free(number);
}

int main(int argc, char const *argv[])
{
    print_numbers3(2);
    return 0;
}
