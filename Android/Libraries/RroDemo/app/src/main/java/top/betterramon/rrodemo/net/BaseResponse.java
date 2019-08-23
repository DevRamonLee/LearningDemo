package top.betterramon.rrodemo.net;

/**
 * Created by Ramon Lee on 2019/8/12.
 */
public class BaseResponse<T> {
    public int errorCode;   // 0 代表请求成功
    public String errorMsg;
    public T data;

    public boolean isSuccess() {
        return errorCode == 0;
    }
}
