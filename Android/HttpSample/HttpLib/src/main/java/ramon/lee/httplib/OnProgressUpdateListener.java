package ramon.lee.httplib;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/22 1:07
 */
interface OnProgressUpdateListener {
    void updateProgress(int curLen, int totalLen);
}
