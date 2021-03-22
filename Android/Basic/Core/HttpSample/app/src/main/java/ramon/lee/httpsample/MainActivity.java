package ramon.lee.httpsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.FileCallback;
import ramon.lee.httplib.Request;
import ramon.lee.httplib.RequestTask;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testHandleException();
    }


    private void testHandleException() {
        String url = "https://scpic.chinaz.net/files/pic/pic9/202103/apic31574.jpg";
        Request request = new Request(url);

        String path = getApplication().getExternalCacheDir().getPath() + File.separator + "test.jpg";
        request.setICallback(new FileCallback() {
            @Override
            public void onSuccess(String path) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: path" + path);
            }

            @Override
            public void onFailure(AppException e) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: onFailure " + e.getMessage());
            }

            @Override
            public void updateProgress(int curLen, int totalLen) {
                Log.i(TAG, "testHttpGetOnSubThreadDownloadProgress: updateProgress " + curLen + "/" + totalLen);
            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);
        request.setOnGlobalExceptionListener(this);
        RequestTask task = new RequestTask(request);
        task.execute();
    }
}