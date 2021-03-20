package ramon.lee.httplib;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 0:12
 */
interface ICallback {
    void onSuccess(String result);
    void onFailure(Exception e);
}
