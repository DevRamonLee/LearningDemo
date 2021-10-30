package ramon.lee.asyncdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/8 8:19
 */
class DownloadFileTask extends AsyncTask<URL, Integer, Long> {
    private static final String TAG = "DownloadFileTask";

    @Override
    protected void onPreExecute() {
        Log.i(TAG,"onPreExecute called");
    }

    @Override
    protected Long doInBackground(URL... urls) {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i ++) {
            Log.i(TAG,"开始下载文件 " + i);
            publishProgress(20);
            if (isCancelled())
                break;
        }
        return totalSize;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG,"进度更新 " + values[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        Log.i(TAG,"下载完成，大小为 " + aLong);
    }
}
