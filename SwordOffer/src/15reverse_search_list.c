/*
 * @Author: Ramon
 * @Date: 2025-05-14 19:26:26
 * @LastEditTime: 2025-05-14 19:59:00
 * @FilePath: /SwordOffer/src/15reverse_search_list.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

typedef struct ListNode {
    int value;
    struct ListNode *next;
} ListNode;

ListNode* reverse_search_list(ListNode *list, int k) {
    if (list == NULL || k <= 0) {
        return NULL;
    }
    ListNode *first = list;
    ListNode *second = list;

    // 先让 first 走 k 步
    for (int i = 0; i < k; i++) {
        if (first == NULL) {
            return NULL; // 链表长度小于 k
        }
        first = first->next;
    }
    while(first != NULL) {
        first = first->next;
        second = second->next;
    }
    return second;
}

int main(int argc, char const *argv[])
{
    ListNode *node1 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node2 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node3 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node4 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node5 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node6 = (ListNode *)malloc(sizeof(ListNode));


    node1->value = 1; node1->next = node2;
    node2->value = 2; node2->next = node3;
    node3->value = 3; node3->next = node4;
    node4->value = 4; node4->next = node5;
    node5->value = 5; node5->next = node6;
    node6->value = 6; node6->next = NULL;

    ListNode *head = node1;

    ListNode *result = reverse_search_list(head, 3);
    if (result != NULL) {
        printf("倒数第 3 个是 %d", result->value);
    } else {
        printf("未找到该元素");
    }

    ListNode *temp;
    while (head) {
        temp = head;
        head = head->next;
        free(temp);
        temp = NULL;
    }
    node1 = node2 = node3 = node4 = node5 = node6 = NULL;
    head = result = NULL;
    return 0;
}
