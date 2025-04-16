package top.betterramon.okhttpdemo2.net;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.RequestBody;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public interface ILoader<T> {
    int EXECUTE_POST_JSON = 10001;  //POST 请求提交Json数据
    int EXECUTE_POST_BODY = 10002;  //POST 请求提交键值对Bean;
    int EXECUTE_POST_MAP = 10003;   //POST 请求提交键值对 Bean;
    int EXECUTE_POST_IMAGE_UPLOADINGS = 10005;  //POST 图片上传多图
    int EXECUTE_POST_IMAGE_UPLOADINGS_KEY = 100051; //POST 图片上传多图 key
    int EXECUTE_POST_IMAGE_UPLOADINGS_KEY_PHOTO_SUBSCRIPTION = 100052;
    int EXECUTE_POST_IMAGE_UPLOADING = 10006;   //POST 图片上传单图（头像）
    int EXECUTE_GET_BODY = 10007;   //GET 请求
    int EXECUTE_POST_FILE = 100053;

    ILoader path(String path);
    ILoader url(String url);
    ILoader list(List<String> list);
    ILoader map(ConcurrentHashMap<String, String> map);
    ILoader cls(Class<T> cls);
    ILoader requestBody(RequestBody body);
    ILoader json(String json);
    ILoader executeFction(int fction);
    ILoader delayed(long time);
    Thread callback(Callback<T> call);

    interface Callback<T> {
        @SuppressWarnings("unchecked")
        void onResult(T data);
        void onError(Error error);
    }
}
