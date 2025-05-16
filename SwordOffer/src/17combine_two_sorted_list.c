/*
 * @Author: Ramon
 * @Date: 2025-05-16 10:07:56
 * @LastEditTime: 2025-05-16 10:50:08
 * @FilePath: /SwordOffer/src/17combine_two_sorted_list.c
 * @Description:
 */
#include <stdlib.h>
#include <stdio.h>

typedef struct ListNode
{
    int value;
    struct ListNode *next;
} ListNode;

ListNode* build_new_list(ListNode *head1, ListNode *head2)
{
	if (head1 == NULL || head2 == NULL) {
		if (head1 != NULL) {
			return head1;
		} else if (head2 != NULL) {
			return head2;
		}
		return NULL;
	}
	ListNode *new_head = NULL;
	ListNode *p1 = head1;
	ListNode *p2 = head2;
	if (p1->value <= p2-> value) {
		new_head = p1;
		p1 = p1->next;
		if (p1 == NULL) {
			new_head->next = p2;
			return new_head;
		}
	} else {
		new_head = p2;
		p2 = p2->next;
		if (p2 == NULL){
			new_head->next = p1;
			return new_head;
		}
	}

	ListNode *current = new_head;
	while(p1 && p2) {
		if (p1->value <= p2->value) {
			current->next = p1;
			current = p1;
			p1 = p1->next;
			if (p1 == NULL){
				current->next = p2;
			}
		} else {
			current->next = p2;
			current = p2;
			p2 = p2->next;
			if (p2 == NULL){
				current->next = p1;
			}
		}
	}
	return new_head;
}

ListNode* build_new_list2(ListNode *head1, ListNode *head2) {

    ListNode dummy;
    ListNode *tail = &dummy;
    dummy.next = NULL;

    while (head1 && head2)
    {
        if (head1->value <= head2->value) {
            tail->next = head1;
            head1 = head1->next;
        } else {
            tail->next = head2;
            head2 = head2->next;
        }
        tail = tail->next;
    }
    tail->next = head1 ? head1 : head2;
    return dummy.next;
}

// 使用递归的方式
ListNode* build_new_list3(ListNode *head1, ListNode *head2)
{
    if (head1 == NULL) return head2;
    if (head2 == NULL) return head1;
    if (head1->value < head2->value) {
        head1->next = build_new_list3(head1->next, head2);
        return head1;
    } else {
        head2->next = build_new_list2(head1, head2->next);
        return head2;
    }
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
    // 创建链表 1 -> 3 -> 5
    ListNode *node1 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node2 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node3 = (ListNode *)malloc(sizeof(ListNode));

    node1->value = 1; node1->next = node2;
    node2->value = 3; node2->next = node3;
    node3->value = 5; node3->next = NULL;
    ListNode *head1 = node1;

    // 创建链表 2 -> 4 -> 6
    ListNode *node4 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node5 = (ListNode *)malloc(sizeof(ListNode));
    ListNode *node6 = (ListNode *)malloc(sizeof(ListNode));

    node4->value = 2; node4->next = node5;
    node5->value = 4; node5->next = node6;
    node6->value = 6; node6->next = NULL;
    ListNode *head2 = node4;

    printf("list1: ");
    print_list(head1);

    printf("list2: ");
    print_list(head2);

    // ListNode *new_head = build_new_list(head1, head2);
    // printf("new_list: ");
    // print_list(new_head);

    // ListNode *new_head2 = build_new_list2(head1, head2);
    // printf("new_list2: ");
    // print_list(new_head2);

    ListNode *new_head3 = build_new_list3(head1, head2);
    printf("new_list3: ");
    print_list(new_head3);

    ListNode *tmp;
    while (head1) {
        tmp = head1;
        head1 = head1->next;
        free(tmp);
    }

    while (head2) {
        tmp = head2;
        head2 = head2->next;
        free(tmp);
    }
    return 0;
}
