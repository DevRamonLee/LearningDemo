package ramon.lee.httplib;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 21:21
 */
public interface ICallback<T> {
    void onSuccess(T o);
    void onFailure(Exception e);
    /**
     * 根据返回的数据类型会有多种解析方式，如 json String bitmap xml 等
     * @param connection
     * @return
     */
    T parse(HttpURLConnection connection) throws Exception;
}
