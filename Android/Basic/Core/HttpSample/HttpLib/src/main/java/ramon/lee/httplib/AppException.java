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

    public AppException(String detailMessage) {
        super(detailMessage);
    }

    public AppException(int status, String responseMsg) {
        super(responseMsg);
        this.statusCode = status;
        this.responseMsg = responseMsg;
    }
}
