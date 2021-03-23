package ramon.lee.httplib;

import java.io.InputStream;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/23 0:47
 */
public class AppException extends Exception {

    public int statusCode;
    public String responseMsg;

    public enum ErrorType {TIMEOUT, SERVER, JSON, IO, FILE_NOT_FOUND, MANUAL}

    public ErrorType errorType;

    public AppException(ErrorType type, String detailMessage) {
        super(detailMessage);
        this.errorType = type;
    }

    public AppException(int status, String responseMsg) {
        super(responseMsg);
        this.errorType = ErrorType.SERVER;
        this.statusCode = status;
        this.responseMsg = responseMsg;
    }
}
