package ramon.lee.httplib;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 21:21
 */
public interface ICallback {
    void onSuccess(Object o);
    void onFailure(Exception e);
}
