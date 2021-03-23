package ramon.lee.httplib;

import java.net.HttpURLConnection;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 21:21
 */
public interface ICallback<T> {
    void onSuccess(T o);
    void onFailure(AppException e);
    /**
     * 根据返回的数据类型会有多种解析方式，如 json String bitmap xml 等
     * @param connection
     * @param listener 进度更新回调
     * @return
     */
    T parse(HttpURLConnection connection, OnProgressUpdateListener listener) throws AppException;
    T parse(HttpURLConnection connection) throws AppException;
    void updateProgress(int curLen, int totalLen);
    void cancel();
}
