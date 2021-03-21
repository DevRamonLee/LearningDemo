package ramon.lee.httpsample;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import ramon.lee.httplib.Callback;
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
        request.setICallback(new Callback<String>(){
            @Override
            public void onSuccess(String o) {
                Log.i(TAG, "testHttpPostOnSubThread: result = " + o);
            }

            @Override
            public void onFailure(Exception e) {
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
        request.setICallback(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: users size is " + users.size());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: onFailure " + e.getMessage());
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
    }
}