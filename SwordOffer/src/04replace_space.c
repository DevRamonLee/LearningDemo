/*
 * @Author: Ramon
 * @Date: 2025-04-15 10:43:39
 * @LastEditTime: 2025-04-15 11:39:20
 * @FilePath: /SwordOffer/src/04replace_space.c
 * @Description: 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 替换空格为 20%
void replace_space(const char *origin_str, int origin_len, char *new_str, int target_len)
{
    int insert_index = target_len - 2; // - 1 for \0, -1 more because index starts at 0
    for (int i = origin_len - 1; i >= 0; i--) {
        if (origin_str[i] == ' ') {
            new_str[insert_index--] = '0';
            new_str[insert_index--] = '2';
            new_str[insert_index--] = '%';
        } else {
            new_str[insert_index--] = origin_str[i];
        }
    }
}

// 统计空格数量
int count_space_num(char *str) {
    int count = 0;
    if (str == NULL) {
        printf("Input str is NULL\n");
        return 0;
    }
    int len = strlen(str);
    for (int i = 0; str[i] != '\0'; i++) {
        if (str[i] == ' ') {
            count++;
        }
    }
    return count;
}

int main(int argc, char const *argv[])
{
    char *str = "we are happy.";
    int origin_len = strlen(str);
    int space_count = count_space_num(str);
    int new_len = origin_len + space_count * 2 + 1; // +1 for \0
    char *new_str = (char*)malloc(new_len * sizeof(char));
    if (new_str == NULL) {
        printf("Malloc failed\n");
        return 1;
    }
    memset(new_str, '\0', new_len);
    replace_space(str, origin_len, new_str, new_len);

    printf("Original: \"%s\"\n", str);
    printf("Replaced: \"%s\"\n", new_str);
    free(new_str);
    return 0;
}
