package ramon.lee.httplib;

import java.util.Map;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/20 23:45
 */
public class Request {
    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public RequestMethod requestMethod;
    public String url;
    public String content;
    public Map<String, String> header;

    public Request(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public Request(String url) {
        this.url = url;
        this.requestMethod = RequestMethod.GET;
    }
}
