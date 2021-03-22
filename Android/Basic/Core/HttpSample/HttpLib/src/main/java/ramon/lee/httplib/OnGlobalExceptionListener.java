package ramon.lee.httplib;

/**
 * @Desc : 处理全局的异常
 * @Author : Ramon
 * @create 2021/3/23 1:33
 */
public interface OnGlobalExceptionListener {
    boolean handleException(AppException e);
}
