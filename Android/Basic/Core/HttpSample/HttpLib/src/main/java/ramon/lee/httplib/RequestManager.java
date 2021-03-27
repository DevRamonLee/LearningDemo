package ramon.lee.httplib;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/26 0:05
 */
public class RequestManager {
    private static RequestManager instance;
    private HashMap<String, ArrayList<Request>> mCachedRequests;
    private static ExecutorService fixedExecutors;

    private RequestManager() {
        mCachedRequests = new HashMap<>();
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
            fixedExecutors = Executors.newFixedThreadPool(5);
        }
        return instance;
    }

    public void performRequest(Request request) {
        request.execute(fixedExecutors);
        if (!mCachedRequests.containsKey(request.tag)) {
            ArrayList<Request> requests = new ArrayList<>();
            mCachedRequests.put(request.tag, requests);
        }
        mCachedRequests.get(request.tag).add(request);
    }

    /**
     * 通过 tag 取消和 Activity 相关联的 Activity
     * @param tag
     */
    public void cancelRequest(String tag) {
        if (tag == null || "".equals(tag.trim())) {
            return;
        }
        if (mCachedRequests.containsKey(tag)) {
            ArrayList<Request> requests = mCachedRequests.remove(tag);
            for (Request request : requests) {
                if (!request.isCanceled && tag.equals(request.tag)) {
                    request.cancel(true);
                }
            }
        }
    }

    public void cancelAll() {
        for (Map.Entry<String, ArrayList<Request>> entry : mCachedRequests.entrySet()) {
            ArrayList<Request> requests = entry.getValue();
            for (Request request : requests) {
                if (!request.isCanceled) {
                    request.cancel(true);
                }
            }
        }
    }
}
