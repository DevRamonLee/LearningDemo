package ramon.lee.httplib;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 22:24
 */
public abstract class Callback<T> implements ICallback<T> {

    @Override
    public T parse(HttpURLConnection connection) throws Exception {
        int state = connection.getResponseCode();
        if (state == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            String result = new String(out.toByteArray());
            JSONObject jsonObject = new JSONObject(result);
            String data = jsonObject.getString("data");
            Gson gson = new Gson();
            TypeToken<ArrayList<T>> token = new TypeToken<ArrayList<T>>() {};
            return gson.fromJson(data, token.getType());
        }
        return null;
    }
}
