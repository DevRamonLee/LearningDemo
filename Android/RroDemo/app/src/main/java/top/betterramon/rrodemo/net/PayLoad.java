package top.betterramon.rrodemo.net;

import rx.functions.Func1;

/**
 * Created by Ramon Lee on 2019/8/12.
 * 剥离出 data 给上层调用者
 */
public class PayLoad<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> tBaseResponse) { //获取数据失败时，包装一个Fault 抛给上层处理错误
        if (!tBaseResponse.isSuccess()) {
            throw new Fault(tBaseResponse.errorCode,tBaseResponse.errorMsg);
        }
        return tBaseResponse.data;
    }
}
