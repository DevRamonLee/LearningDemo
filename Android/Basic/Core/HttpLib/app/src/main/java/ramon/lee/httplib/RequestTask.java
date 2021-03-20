package ramon.lee.httplib;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 0:07
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {
    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return HttpUrlConnectionUtil.execute(request);
        } catch (IOException e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception) {
            request.iCallback.onFailure((Exception) o);
        } else {
            request.iCallback.onSuccess((String)o);
        }
    }
}
