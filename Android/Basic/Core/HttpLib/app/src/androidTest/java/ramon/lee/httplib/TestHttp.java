package ramon.lee.httplib;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        String result = HttpUrlConnectionUtil.execute(request);
        Log.i(TAG, "testHttpGet: result = " + result);
    }

    @Test
    public void testHttpPost() throws Throwable {
        String url = "https://www.wanandroid.com/lg/uncollect_originId/2333/json";
        String content = "";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        String result = HttpUrlConnectionUtil.execute(request);
        Log.i(TAG, "testHttpPost: result = " + result);
    }
}
