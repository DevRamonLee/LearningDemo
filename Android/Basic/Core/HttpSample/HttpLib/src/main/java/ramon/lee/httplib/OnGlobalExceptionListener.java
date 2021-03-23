package ramon.lee.httplib;

/**
 * @Desc : 处理全局的异常
 * @Author : Ramon
 * @create 2021/3/23 1:33
 */
public interface OnGlobalExceptionListener {
    // true 表示已经处理了异常， false 表示没有处理，交给 onFailure 回调处理
    boolean handleException(AppException e);
}
