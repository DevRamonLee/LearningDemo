package ramon.lee.httpsample;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.FileCallback;
import ramon.lee.httplib.JsonCallback;
import ramon.lee.httplib.Request;
import ramon.lee.httplib.RequestTask;
import ramon.lee.httpsample.data.User;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/20 1:53
 */
@RunWith(AndroidJUnit4.class)
public class TestHttp {
    private final String TAG = "TestHttp";

    @Test
    public void testHttpGet() throws Throwable {
        String url = "https://wanandroid.com/wxarticle/chapters/json";
        Request request = new Request(url);
//        String result = HttpUrlConnectionUtil.execute(request);
//        Log.i(TAG, "testHttpGet: result = " + result);
    }

    @Test
    public void testHttpPost() throws Throwable {
        String url = "https://www.wanandroid.com/lg/uncollect_originId/2333/json";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = "";
//        String result = HttpUrlConnectionUtil.execute(request);
//        Log.i(TAG, "testHttpPost: result = " + result);
    }

    @Test
    public void testHttpPostOnSubThread() {
        String url = "https://www.wanandroid.com/lg/uncollect_originId/2333/json";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = "";
        request.setICallback(new JsonCallback<String>(){
            @Override
            public void onSuccess(String o) {
                Log.i(TAG, "testHttpPostOnSubThread: result = " + o);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpPostOnSubThread: result = " + e.getMessage());
            }
        });
        RequestTask requestTask = new RequestTask(request);
        requestTask.execute();
    }

    @Test
    public void testHttpGetOnSubThreadGeneric() throws Throwable {
        String url = "https://wanandroid.com/wxarticle/chapters/json";
        Request request = new Request(url);
        request.setICallback(new JsonCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: users size is " + users.size());
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: onFailure " + e.getMessage());
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    @Test
    public void testHttpGetOnSubThreadDownload() throws Throwable {
        String url = "https://scpic.chinaz.net/files/pic/pic9/202103/apic31574.jpg";
        Request request = new Request(url);
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        String path = appContext.getExternalCacheDir().getPath() + File.separator + "test.jpg";
        request.setICallback(new FileCallback() {
            @Override
            public void onSuccess(String path) {
                Log.i(TAG, "testHttpGetOnSubThreadDownload: path" + path);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadDownload: onFailure " + e.getMessage());
            }
        }.setCachePath(path));
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    @Test
    public void testHttpGetOnSubThreadDownloadProgress() throws Throwable {
        String url = "https://scpic.chinaz.net/files/pic/pic9/202103/apic31574.jpg";
        Request request = new Request(url);
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestTask task = new RequestTask(request);
        String path = appContext.getExternalCacheDir().getPath() + File.separator + "test.jpg";
        request.setICallback(new FileCallback() {
            @Override
            public void onSuccess(String path) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: path" + path);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: onFailure " + e.getMessage());
                if (e.statusCode == 403) {
                    if ("password incorrect".equals(e.responseMsg)) {
                        // TODO: 提示
                    } else if ("token invalid".equals(e.responseMsg)) {
                        // TODO: reLogin
                    }
                }
            }

            @Override
            public void updateProgress(int curLen, int totalLen) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: updateProgress " + curLen + "/" + totalLen);
            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);
        task.execute();
    }


    @Test
    public void testHttpGetOnSubThreadDownloadCancel() throws Throwable {
        String url = "https://scpic.chinaz.net/files/pic/pic9/202103/apic31574.jpg";
        Request request = new Request(url);
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestTask task = new RequestTask(request);
        String path = appContext.getExternalCacheDir().getPath() + File.separator + "test.jpg";
        request.setICallback(new FileCallback() {
            @Override
            public void onSuccess(String path) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadCancel: path" + path);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadCancel: onFailure " + e.getMessage());
                if (e.statusCode == 403) {
                    if ("password incorrect".equals(e.responseMsg)) {
                        // TODO: 提示
                    } else if ("token invalid".equals(e.responseMsg)) {
                        // TODO: reLogin
                    }
                }
                e.printStackTrace();
            }

            @Override
            public void updateProgress(int curLen, int totalLen) {
                if (curLen * 100L / totalLen > 20) {
                    // 取消请求，只能在 doInBackground 执行完之后取消
//                    task.cancel(true);
                    request.cancel();
                }
                Log.i(TAG, "testHttpGetOnSubThreadDownloadCancel: updateProgress " + curLen + "/" + totalLen);
            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);
        task.execute();
    }
}