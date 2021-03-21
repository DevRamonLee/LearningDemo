package ramon.lee.httplib;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 20:04
 */
public class HttpUrlConnectionUtil {

    public static HttpURLConnection execute(Request request) throws IOException {
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
        }
        return null;
    }

    private static HttpURLConnection get(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.method.name());
        connection.setReadTimeout(15 * 3000);
        connection.setConnectTimeout(15 * 3000);
        addHeader(connection, request.header);
        return connection;
    }

    private static HttpURLConnection post(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.method.name());
        connection.setReadTimeout(15 * 3000);
        connection.setConnectTimeout(15 * 3000);
        connection.setDoOutput(true);

        addHeader(connection, request.header);
        return connection;
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> header) {
        if (header == null || header.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry: header.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}
