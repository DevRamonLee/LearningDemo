/**
 * @Desc :把字符串中每个空格替换成 20%
 * @Author : Ramon
 * @create 2021/2/2 0:01
 */
public class ReplaceSpaces_05_01 {
    public static void main(String[] args) {
        String[] test = {"Hello", " ", "my", " ", "friend"};
        replaceBlank(test);
    }

    private static void replaceBlank(String[] strs) {
        if (strs == null || strs.length <= 0) return;
        // 原始字符串的长度
        int originalLength = strs.length;
        // 空格的个数
        int numberOfBlank = 0;
        int i = 0;
        while (i < strs.length) {
            if (" ".equals(strs[i]))
                ++numberOfBlank;
            ++i;
        }
        // 创建一个新的数组，长度为原始长度加上空格 * 2
        int newLength = originalLength + numberOfBlank * 2;
        String[] newStrs = new String[newLength];

        int indexOfOriginal = originalLength - 1;
        int indexOfNew = newLength - 1;
        while (indexOfOriginal >= 0 && indexOfNew >= indexOfOriginal) {
            if (" ".equals(strs[indexOfOriginal])) {
                newStrs[indexOfNew--] = "0";
                newStrs[indexOfNew--] = "2";
                newStrs[indexOfNew--] = "%";
            } else {
                newStrs[indexOfNew--] = strs[indexOfOriginal];
            }
            --indexOfOriginal;
        }
        System.out.println("original strs: " + printStringArray(strs));
        System.out.println("new strs: " + printStringArray(newStrs));
    }

    private static String printStringArray(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String str : array) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
