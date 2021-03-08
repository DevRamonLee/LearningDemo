/**
 * @Desc : 在一个长度为 n+1 的数组里的所有数字都在 1~n 的范围内，
 * 所以这个数组中至少有一个数字是重复的，请找出数组中任意一个重复的数字，但不能修改输入的数组。
 * 例如，如果输入的长度为8 的数组{2，3，5，4，3，2，6，7}，那么对应的输出是重复数字 2 或者 3
 * @Author : Ramon
 * @create 2021/1/27 22:51
 */
public class DuplicationInArrayNoEdit_03_02 {
    public static void main(String[] args) {
        int[] array = {2,3,5,4,3,2,6,7};
        System.out.println("duplicate number is " + getDuplication(array, 8));
    }

    public static int getDuplication(int[] array, int length) {
        if(array == null || length <= 0) {
            return -1;
        }
        int start = 1;
        int end = length -1;    // 长度为 n + 1， 范围是 1~n，所以这里是 length -1
        while (end >= start) {
            int middle = ((end - start) >> 1) + start;
            int count = countRange(array, length, start, middle);
            // 开始等于结束，最后一个数
            if (end == start) {
                if (count > 1) {
                    return start;   // 找到重复
                } else {
                    break;  // 未找到，退出循环
                }
            }
            if (count > (middle - start + 1)) { // 出现次数大于数字个数，这部分有重复的
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }

    private static int countRange(int [] array, int length, int start, int end) {
        if (array == null)
            return 0;
        int count = 0;
        for (int i = 0; i < length; i++) {
            // start 到 end 出现的次数
            if (array[i] >= start && array[i] <= end) {
                ++count;
            }
        }
        return count;
    }
}
