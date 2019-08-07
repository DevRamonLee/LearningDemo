package top.betterramon.okhttpdemo2.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class GsonTools {
    public GsonTools() {
    }

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;

    }

    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        if (cls == null) throw new IllegalArgumentException("cls can not be null");
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("result ====>  ").append(gsonString);
        Gson gson = new Gson();
        return gson.fromJson(gsonString, cls);

    }

    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        if (cls == null) throw new IllegalArgumentException("cls can not be null");
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <T> List<Map<String, T>> changeGsonToListMaps(
            String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }
}
