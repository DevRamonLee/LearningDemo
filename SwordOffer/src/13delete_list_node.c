/*
 * @Author: Ramon
 * @Date: 2025-05-12 11:52:59
 * @LastEditTime: 2025-05-12 12:30:40
 * @FilePath: /SwordOffer/src/13delete_list_node.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

typedef struct ListNode {
    int value;
    struct ListNode *next;
} ListNode;

void delet_node(ListNode **list_head, ListNode *to_be_deleted)
{
    if (!list_head || !to_be_deleted) {
        return;
    }
    // 要删除的节点不是尾节点
    if (to_be_deleted->next != NULL) {
        ListNode *temp = to_be_deleted->next;
        to_be_deleted->value = temp->value;
        to_be_deleted->next = temp->next;
        free(temp);
    } else if (*list_head == to_be_deleted) {
        // 链表中只有一个节点，删除头节点（也是尾节点）
        free(to_be_deleted);
        *list_head = NULL;
    } else {
        // 要删除的元素是链表的尾节点
        ListNode *temp = *list_head;
        while (temp->next != to_be_deleted)
        {
            temp = temp->next;
        }
        temp->next = NULL;
        free(to_be_deleted);
    }
}

void print_list(ListNode *head) {
    while (head) {
        printf("%d -> ", head->value);
        head = head->next;
    }
    printf("NULL\n");
}

int main() {
    // 创建链表 1 -> 2 -> 3
    ListNode *node1 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node2 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node3 = (ListNode *)malloc(sizeof(ListNode));

    node1->value = 1; node1->next = node2;
    node2->value = 2; node2->next = node3;
    node3->value = 3; node3->next = NULL;

    ListNode *head = node1;

    printf("原链表: ");
    print_list(head);

    // 删除 node2（值为2的节点）
    delet_node(&head, node2);

    printf("删除值为2的节点后: ");
    print_list(head);

    // ❗️重新获取尾节点，因为上面一步删除的时候实际不是删除了 node2，而是删除了 node3，所以需要重新获取尾节点
    ListNode *last = head;
    while (last->next != NULL) {
        last = last->next;
    }

    // 删除尾节点
    delet_node(&head, last);

    printf("删除尾节点后: ");
    print_list(head);

    // 删除头节点 node1（也是唯一节点）
    delet_node(&head, head);

    printf("删除唯一节点后: ");
    print_list(head);

    return 0;
}

