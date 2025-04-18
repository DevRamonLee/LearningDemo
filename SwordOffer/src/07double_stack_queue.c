/*
 * @Author: Ramon
 * @Date: 2025-04-18 11:14:01
 * @LastEditTime: 2025-04-18 11:48:02
 * @FilePath: /SwordOffer/src/07double_stack_queue.c
 * @Description: 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_STACK_SIZE 100

typedef struct Stack {
    int values[MAX_STACK_SIZE];
    int top;
} Stack;

void initStack(Stack *stack) {
    if (stack == NULL) {
        printf("[%s] stack is null", __func__);
        return;
    }
    stack->top = -1;
}

bool isStackEmpty(Stack *stack) {
    if (stack == NULL) {
        printf("[%s] stack is null", __func__);
        return true;
    }
    if (stack->top == -1) {
        return true;
    }
    return false;
}

int pushStack(Stack *stack, int value) {
    if (stack == NULL) {
        printf("[%s] stack is null", __func__);
        return -1;
    }
    if (stack->top == MAX_STACK_SIZE - 1) {
        printf("[%s] stack is full", __func__);
        return -1;
    }
    stack->values[++stack->top] = value;
    return 0;
}

int popStack(Stack *stack) {
    if (isStackEmpty(stack)) {
        printf("[%s] stack is empty", __func__);
        return -1;
    }
    return stack->values[stack->top--];
}

void appendTail(Stack *s1, Stack *s2, int value) {
    pushStack(s1, value);
}

void deleteHead(Stack *s1, Stack *s2) {
    if (s1 == NULL || s2 == NULL) {
        printf("[%s] invalid parameter, stack is null", __func__);
        return;
    }
    if (isStackEmpty(s1) && isStackEmpty(s2)) {
        printf("[%s] Queue is empty\n", __func__);
        return;
    }
    if (isStackEmpty(s2)) {
        while (!isStackEmpty(s1))
        {
            pushStack(s2, popStack(s1));
        }
    }
    int result = popStack(s2);
    printf("pop %d\n", result);
}

int main(int argc, char const *argv[])
{
    Stack *stack1 = (Stack*)malloc(sizeof(Stack));
    Stack *stack2 = (Stack*)malloc(sizeof(Stack));
    if (stack1 == NULL || stack2 == NULL) {
        printf("[%s] malloc failed", __func__);
        return 1;
    }
    initStack(stack1);
    initStack(stack2);
    appendTail(stack1, stack2, 3);
    appendTail(stack1, stack2, 5);
    appendTail(stack1, stack2, 8);
    deleteHead(stack1, stack2);
    deleteHead(stack1, stack2);
    deleteHead(stack1, stack2);
    deleteHead(stack1, stack2);
    free(stack1);
    free(stack2);
    return 0;
}
