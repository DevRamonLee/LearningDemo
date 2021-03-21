package ramon.lee.httplib;

import android.os.AsyncTask;

import java.io.IOException;

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
        try {
            return HttpUrlConnectionUtil.execute(request);
        } catch (IOException e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o instanceof String) {
            request.callback.onSuccess((String)o);
        } else {
            request.callback.onFailure((Exception) o);
        }
    }
}
