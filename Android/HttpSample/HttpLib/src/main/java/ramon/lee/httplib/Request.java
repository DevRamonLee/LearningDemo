package ramon.lee.httplib;

import android.os.Build;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 20:34
 */
public class Request {
    private RequestTask requestTask;
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
    public ICallback callback;
    public Boolean enableProgressUpdated = false;   // 是否启用进度更新
    public OnGlobalExceptionListener globalExceptionListener;   // 处理 App 异常
    public int maxRetryCount = 3;
    public String tag;

    public volatile boolean isCanceled = false;

    public Request(String uri) {
        this.url = uri;
        this.method = RequestMethod.GET;
    }

    public Request(String uri, RequestMethod method) {
        this.url = uri;
        this.method = method;
    }

    public void setICallback(ICallback iCallback) {
        this.callback = iCallback;
    }

    /**
     * 允许进度更新
     * @param b
     */
    public void enableProgressUpdated(boolean b) {
        this.enableProgressUpdated = b;
    }

    public void setOnGlobalExceptionListener(OnGlobalExceptionListener listener) {
        this.globalExceptionListener = listener;
    }

    public void cancel(boolean force) {
        this.isCanceled = true;
        callback.cancel();
        if (force && requestTask != null) {
            requestTask.cancel(force);
        }
    }

    public void checkIfCanceled() throws AppException {
        if (isCanceled) {
            throw new AppException(AppException.ErrorType.CANCEL, "request has been cancelled");
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void execute(ExecutorService executorService) {
        requestTask = new RequestTask(this);
        if (Build.VERSION.SDK_INT > 11) {
            requestTask.executeOnExecutor(executorService);
        } else {
            requestTask.execute();
        }
    }
}
