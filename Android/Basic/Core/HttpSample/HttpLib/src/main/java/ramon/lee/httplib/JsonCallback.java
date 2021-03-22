package ramon.lee.httplib;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Desc : json 反序列化实体类
 * @Author : Ramon
 * @create 2021/3/21 22:24
 */
public abstract class JsonCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String result) throws AppException{
        try {
            JSONObject jsonObject = new JSONObject(result);
            String data = jsonObject.getString("data");
            Gson gson = new Gson();
            // 获取泛型的 Type
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return gson.fromJson(data, type);
        } catch (JSONException e) {
            throw new AppException(e.getMessage());
        }
    }
}
