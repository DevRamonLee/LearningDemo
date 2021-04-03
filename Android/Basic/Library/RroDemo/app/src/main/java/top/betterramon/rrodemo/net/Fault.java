package top.betterramon.rrodemo.net;

/**
 * Created by Ramon Lee on 2019/8/12.
 * 异常处理类，将异常包装成一个 Fault ,抛给上层统一处理
 */
public class Fault extends RuntimeException {
    private int errorCode;

    public Fault(int errorCode,String message){
        super(message);
        errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
