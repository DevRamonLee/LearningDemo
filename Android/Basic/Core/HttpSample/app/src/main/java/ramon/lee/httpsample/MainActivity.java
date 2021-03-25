package ramon.lee.httpsample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.FileCallback;
import ramon.lee.httplib.JsonCallback;
import ramon.lee.httplib.Request;
import ramon.lee.httplib.RequestManager;
import ramon.lee.httplib.RequestTask;
import ramon.lee.httpsample.data.User;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testHttpGetOnSubThreadGenericPostRequest();
            }
        });
    }

    public void testHttpGetOnSubThreadDownloadCancel() {
        String url = "https://scpic.chinaz.net/files/pic/pic9/202103/apic31574.jpg";
        Request request = new Request(url);
        request.setOnGlobalExceptionListener(this);
        String path = getApplication().getExternalCacheDir().getPath() + File.separator + "test.jpg";
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
//                    request.cancel();
                }
                Log.i(TAG, "testHttpGetOnSubThreadDownloadCancel: updateProgress " + curLen + "/" + totalLen);
            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);
        request.setTag(toString());
        RequestManager.getInstance().performRequest(request);
    }

    public void testHttpGetOnSubThreadGenericPostRequest() {
        String url = "https://wanandroid.com/wxarticle/chapters/json";
        Request request = new Request(url);
        request.setICallback(new JsonCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: users[] name is " + users.get(0).getName());
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: users size is " + users.size());
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadGeneric: onFailure " + e.getMessage());
            }

            @Override
            public List<User> postRequest(List<User> users) {
                // TODO: insert to db or do data filter
                users.get(0).setName("蜡笔笔芯");
                return users;
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
    }
}