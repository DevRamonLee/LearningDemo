/*
 * @Author: Ramon
 * @Date: 2025-04-29 09:35:07
 * @LastEditTime: 2025-04-29 09:46:29
 * @FilePath: /SwordOffer/src/09feibo_sequence.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

int fiboo_sequence(int n) {
    if (n == 0 || n == 1) {
        return n;
    }
    return fiboo_sequence(n - 1) + fiboo_sequence(n - 2);
}

int fiboo_sequence_iterator(int n) {
    if (n == 0 || n == 1) {
        return n;
    }
    int m = 0;
    int p = 1;
    int result = 0;
    for (int i = 2; i <= n; i++) {
        result = m + p;
        m = p;
        p = result;
    }
    return result;
}

int main(int argc, char const *argv[])
{
    for (int i = 0; i < 10; i++) {
        // printf(" %d ", fiboo_sequence(i));
        printf(" %d ", fiboo_sequence_iterator(i));
    }
    return 0;
}
