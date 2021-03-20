package ramon.lee.httplib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/20 1:42
 */
public class HttpUrlConnectionUtil {

    public static String execute(Request request) throws IOException {
        switch (request.requestMethod) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
        }
        return null;
    }

    private static String get(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.requestMethod.name());
        connection.setConnectTimeout(15 * 3000);
        connection.setReadTimeout(15 * 3000);

        addHeader(connection, request.header);
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            return new String(out.toByteArray());
        }
        return null;
    }

    private static String post(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.requestMethod.name());
        connection.setConnectTimeout(15 * 3000);
        connection.setReadTimeout(15 * 3000);
        connection.setDoOutput(true);

        addHeader(connection, request.header);

        if (request.content != null) {
            OutputStream os = connection.getOutputStream();
            os.write(request.content.getBytes());
        }

        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            return new String(out.toByteArray());
        }
        return null;
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> header) {
        if (header == null || header.size() == 0)
            return;
        for(Map.Entry<String, String> entry : header.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}
