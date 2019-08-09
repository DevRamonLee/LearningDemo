package ramon.better.com.retrofitdemo.beans;

import java.util.List;

/**
 * Created by Ramon Lee on 2019/8/8.
 */
public class OfficialAccounts {
    private List<Data> data;
    private int errorCode;
    private String errorMsg;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "OfficialAccounts{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
