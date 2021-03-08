/**
 * @Desc :  在一个二维数组中，每一行都按照从左到右递增的顺序排列，每一列都按照从上到下的顺序排列，
 * 请完成一个函数，输入这样一个二维数组和一个整数，判断数组中是否包含该整数。
 * @Author : Ramon
 * @create 2021/1/27 23:38
 */
public class FindInPartiallySortedMatrix_04 {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,8,9}, {2,4,9,12},{4,7,10,13},{6,8,11,15}};
        System.out.println("find 7 : " + find(matrix, 4, 4, 7));
    }

    public static boolean find(int[][] matrix, int rows, int columns, int number) {
        boolean found = false;
        if (matrix != null && rows > 0 && columns > 0) {
            int row = 0;
            int column = columns - 1;
            // row column 的值表示从最右端开始遍历
            while (row < rows && column >= 0) {
                if (matrix[row][column] == number) {
                    found = true;
                    break;
                } else if (matrix[row][column] > number) {
                    -- column;
                } else {
                    ++ row;
                }
            }
        }
        return found;
    }
}
