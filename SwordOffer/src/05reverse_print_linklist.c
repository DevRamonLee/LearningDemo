/*
 * @Author: Ramon
 * @Date: 2025-04-16 12:27:02
 * @LastEditTime: 2025-04-16 13:21:25
 * @FilePath: /SwordOffer/src/05reverse_print_linklist.c
 * @Description: 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct ListNode
{
    int value;
    struct ListNode *p_next;
} ListNode;


void create_list(ListNode *head, int count) {
    if (head == NULL || count <= 0) {
        printf("[%s] invalid parameters: head=%p, count=%d\n", __func__, head, count);
        return;
    }
    ListNode *p = head;
    while (count > 0)
    {
        ListNode *node = (ListNode*)malloc(sizeof(ListNode));
        if (node == NULL) {
            printf("[%s] create node failed\n", __func__);
            return;
        }
        node ->value = rand() % 100;
        node ->p_next = NULL;
        
        // 插入末尾
        p->p_next = node;
        p = node;
        count--;
    }
}

// 逆序打印链表
int reverse_print_list(const ListNode *head) {
    if (head == NULL) {
        printf("[%s] head is null\n", __func__);
        return -1;
    }
    int count = 0;
    const ListNode *node = head->p_next;
    while (node) {
        count++;
        node = node->p_next;
    }
    int *array = (int*)malloc(count * sizeof(int));
    if (!array) {
        printf("[%s] malloc failed\n", __func__);
        return -1;
    }
    int i = 0;
    node = head->p_next;
    while (node != NULL)
    {
       printf(" %d ", node->value);
       array[i++] = node->value;
       node = node->p_next;
    }
    printf("\n");
    for (int j = count - 1; j >= 0; j--) {
        printf(" %d ", array[j]);
    }
    free(array);
    return 0;
}

void reverse_print_recursive(ListNode *head) {
    if (head == NULL) return;
    reverse_print_recursive(head -> p_next);
    printf(" %d ", head->value);
}

int main(int argc, char const *argv[])
{
    srand((unsigned int)time(NULL));
    ListNode *head = (ListNode*)malloc(sizeof(ListNode));
    if (head == NULL) {
        printf("Head malloc failed");
        return 1;
    }
    create_list(head, 10);
    reverse_print_list(head);
    printf("\n");
    reverse_print_recursive(head->p_next);
    ListNode *cur = head;
    while (cur) {
        ListNode *next = cur->p_next;
        free(cur);
        cur = next;
    }
    return 0;
}

