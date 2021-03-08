/**
 * @Desc : 面试题 3： 找出数组中重复的数字
 * 题目1： 在一个长度为 n 的数组里的所有数字都在 0 ~ n-1 的范围内，数组中某些数字是重复的，
 * 但不知道哪几个数组重复了，也不知道重复了几次。请找出数组中任意一个重复的数字。
 * 例如，如果输入长度为 7 的数组{2,3,1,0,2,5,3},那么对应的输出是重复的数字 2 或者 3
 * @Author : Ramon
 * @create 2021/1/26 23:18
 */
public class DuplicationInArray_03_01 {

    public static void main(String[] args) {
        int[] a = {2,3,4,1,4};
        System.out.println("a has duplicate number: " + findDuplicateNumber(a, 5));
    }

    private static boolean findDuplicateNumber(int[] array, int length) {
        if (array == null || length <= 0) {
            // 处理无效输入
            return false;
        }

        for(int i=0; i < length; ++i) {
            // 输入不满足 0 ~ n-1 之间
            if (array[i] < 0 || array[i]> length -1) {
                return false;
            }
        }

        for(int i = 0; i < length; ++i) {
            while (array[i] != i) {
                // 当前数组在 i 和 位置 array[i] 都出现了，表示重复
                if (array[i] == array[array[i]]) {
                    return true;
                }

                // 把 array[i] 的值换到对应下标位置
                int temp = array[i];
                array[i] = array[array[i]];
                array[temp] = temp;
            }
        }
        return false;
    }
}
