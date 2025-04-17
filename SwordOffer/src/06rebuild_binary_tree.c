/*
 * @Author: Ramon
 * @Date: 2025-04-17 11:10:59
 * @LastEditTime: 2025-04-17 12:38:09
 * @FilePath: /SwordOffer/src/06rebuild_binary_tree.c
 * @Description: 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct BinaryTreeNode
{
    int value;
    struct BinaryTreeNode *left;
    struct BinaryTreeNode *right;
} BinaryTreeNode;

// 根据前序遍历根节点的值找到中序遍历根节点的位置
int findRootIndexInOrder(int rootValue, int *inOrder, int inStart, int inEnd) {
    for (int i = inStart; i <= inEnd; i++) {
        if (rootValue == inOrder[i]) {
            return i;
        }
    }
    return -1;
}

BinaryTreeNode* buildTree(int *preOrder, int preStart, int preEnd, int *inOrder, int inStart, int inEnd) {
    if (preStart > preEnd || inStart > inEnd) return NULL;
    int rootValue = preOrder[preStart];
    BinaryTreeNode *root = (BinaryTreeNode*)malloc(sizeof(BinaryTreeNode));
    root->value = rootValue;
    root->left = root->right = NULL;

    int rootIndexInOrder = findRootIndexInOrder(rootValue, inOrder, inStart, inEnd);
    int leftSize = rootIndexInOrder - inStart;
    root->left = buildTree(preOrder, preStart + 1, preStart + leftSize, inOrder, inStart, rootIndexInOrder - 1);
    root->right = buildTree(preOrder, preStart + leftSize + 1, preEnd, inOrder, rootIndexInOrder + 1, inEnd);
    return root;
}

BinaryTreeNode* binaryTreeRebuild(int *preOrder, int *inOrder, int length) {
    return buildTree(preOrder, 0, length - 1, inOrder, 0, length - 1);
}

void printInOrder(BinaryTreeNode *root) {
    if (root == NULL) return;
    printInOrder(root->left);
    printf(" %d ", root->value);
    printInOrder(root->right);
}

int main(int argc, char const *argv[])
{
    int preOrder[] = {1, 2, 4, 7, 3, 5, 6, 8};
    int inOrder[] = {4, 7, 2, 1, 5 ,3 ,8, 6};
    int length = sizeof(preOrder)/sizeof(preOrder[0]);

    BinaryTreeNode *root = binaryTreeRebuild(preOrder, inOrder, length);
    printInOrder(root);
    return 0;
}
