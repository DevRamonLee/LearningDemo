package top.betterramon.okhttpdemo.utils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by limeng on 2019/3/31.
 */

public abstract class BaseCallBack<T> {
    public  abstract void onBeforeRequest(Request request);
    public abstract  void onFailure(Request request, Exception e) ;
    /**
     *请求成功时调用此方法
     * @param response
     */
    public abstract  void onResponse(Response response);
    /**
     *
     * 状态码大于200，小于300 时调用此方法
     * @param response
     * @param t
     * @throws
     */
    public abstract void onSuccess(Response response,T t) ;

    /**
     * 状态码400，404，403，500等时调用此方法
     * @param response
     * @param code
     * @param e
     */
    public abstract void onError(Response response, int code,Exception e) ;

    /**
     * Token 验证失败。状态码401,402,403 等时调用此方法
     * @param response
     * @param code

     */
    public abstract void onTokenError(Response response, int code);
}
