package ramon.lee.httplib;

import android.net.Uri;

import java.util.Map;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 20:34
 */
public class Request {
    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public String url;
    public String content;
    public Map<String, String> header;
    public RequestMethod method;

    public Request(String uri) {
        this.url = uri;
        this.method = RequestMethod.GET;
    }

    public Request(String uri, RequestMethod method) {
        this.url = uri;
        this.method = method;
    }
}
