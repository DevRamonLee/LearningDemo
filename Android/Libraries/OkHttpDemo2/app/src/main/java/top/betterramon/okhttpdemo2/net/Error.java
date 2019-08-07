package top.betterramon.okhttpdemo2.net;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class Error {
    public String error;
    public int code;
    public Error() {}

    public Error(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error{" +
                "error='" + error + '\'' +
                ", code=" + code +
                '}';
    }

    public boolean isSuccess(){
        return code == 0;
    }
}
