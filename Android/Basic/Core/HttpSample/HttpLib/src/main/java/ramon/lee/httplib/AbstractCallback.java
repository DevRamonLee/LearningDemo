package ramon.lee.httplib;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;

/**
 * @Desc : 将 JsonCallback 和 XmlCallback 中的公共部分提取出来
 * @Author : Ramon
 * @create 2021/3/21 23:38
 */
public abstract class AbstractCallback<T> implements ICallback<T>{
    String path;

    @Override
    public T parse(HttpURLConnection connection) throws AppException {
        return parse(connection, null);
    }

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdateListener listener) throws AppException {
        try {
            int state = connection.getResponseCode();
            if (state == HttpURLConnection.HTTP_OK) {
                if (path == null) {
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
                    String result = new String(out.toByteArray());
                    return bindData(result);
                } else {
                    File file = new File(path);
                    if (!file.exists()) file.createNewFile();

                    FileOutputStream out = new FileOutputStream(path);
                    InputStream is = connection.getInputStream();
                    byte[] buffer = new byte[2048];
                    int totalLen = connection.getContentLength();
                    int curLen = 0;
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                        curLen += len;
                        listener.updateProgress(curLen, totalLen);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    Log.i("AbstractCallback", "path " + path);
                    return bindData(path);
                }
            } else {
                String responseMsg = connection.getResponseMessage();
                throw new AppException(state, responseMsg);
            }
        } catch (InterruptedIOException e) {
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
    }

    @Override
    public void updateProgress(int curLen, int totalLen) {

    }

    protected abstract T bindData(String result) throws AppException;

    public ICallback setCachePath(String path) {
        this.path = path;
        return this;
    }
}
