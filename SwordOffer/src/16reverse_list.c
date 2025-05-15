/*
 * @Author: Ramon
 * @Date: 2025-05-15 10:34:46
 * @LastEditTime: 2025-05-15 11:48:35
 * @FilePath: /SwordOffer/src/16reverse_list.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

typedef struct ListNode
{
    int value;
    struct ListNode *next;
} ListNode;

ListNode* reverse_list(ListNode *head)
{
    if (head == NULL)
    {
        return NULL;
    }
    ListNode *pre = NULL;
    ListNode *current = head;
    ListNode *next = NULL;

    while (current)
    {
        next = current->next;
        current->next = pre;
        pre = current;
        current = next;
    }
    return pre;
}

ListNode* reverse_list_recursive(ListNode* head) {
    if (head == NULL || head->next == NULL) {
        return head;
    }

    ListNode* new_head = reverse_list_recursive(head->next);

    head->next->next = head;
    head->next = NULL;

    return new_head;
}


void print_list(ListNode *head) {
    while (head) {
        printf("%d -> ", head->value);
        head = head->next;
    }
    printf("NULL\n");
}

int main(int argc, char const *argv[])
{
    // 创建链表 1 -> 2 -> 3
    ListNode *node1 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node2 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node3 = (ListNode *)malloc(sizeof(ListNode));

    node1->value = 1; node1->next = node2;
    node2->value = 2; node2->next = node3;
    node3->value = 3; node3->next = NULL;
    ListNode *head = node1;

    print_list(head);
    ListNode *reverse_head = reverse_list(head);
    print_list(reverse_head);

    ListNode *new_head = reverse_list_recursive(reverse_head);
    print_list(new_head);

    ListNode *tmp;
    while (reverse_head) {
        tmp = reverse_head;
        reverse_head = reverse_head->next;
        free(tmp);
    }

    return 0;
}
