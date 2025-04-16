package ramon.lee.httplib;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 21:18
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {
    Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... voids) {
        if (request.callback != null) {
            Object o = request.callback.preRequest();
            if (o != null) {
                // 这里假如从数据库读取值之后直接返回了，如果要继续请求网络需要我们自己实现一个线程池
                return o;
            }
        }
        return request(0);
    }

    private Object request(int retry) {
        try {
            HttpURLConnection connection =  HttpUrlConnectionUtil.execute(request);
            if (request.enableProgressUpdated) {
                return request.callback.parse(connection, new OnProgressUpdateListener() {
                    @Override
                    public void updateProgress(int curLen, int totalLen) {
                        publishProgress(curLen, totalLen);
                    }
                });
            } else {
                return request.callback.parse(connection);
            }
        } catch (AppException e) {
            if (e.errorType == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount) {
                    retry++;
                    request(retry);
                }
            }
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o instanceof AppException) {
            if (request.globalExceptionListener != null) {
                if (!request.globalExceptionListener.handleException((AppException) o)) {
                    request.callback.onFailure((AppException) o);
                }
            } else {
                request.callback.onFailure((AppException) o);
            }
        } else {
            request.callback.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        request.callback.updateProgress(values[0], values[1]);
    }
}
