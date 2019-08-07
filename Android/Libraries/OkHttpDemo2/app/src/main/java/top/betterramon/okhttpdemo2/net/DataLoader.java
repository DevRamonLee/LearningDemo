package top.betterramon.okhttpdemo2.net;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.RequestBody;
import top.betterramon.okhttpdemo2.Program;
import top.betterramon.okhttpdemo2.R;
import top.betterramon.okhttpdemo2.bean.BaseDataBean;
import top.betterramon.okhttpdemo2.bean.DatasBean;
import top.betterramon.okhttpdemo2.bean.FileBean;
import top.betterramon.okhttpdemo2.utils.FileUtils;
import top.betterramon.okhttpdemo2.utils.GsonTools;
import top.betterramon.okhttpdemo2.utils.NetUtils;
import top.betterramon.okhttpdemo2.utils.Utils;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class DataLoader<T> extends Loader implements ILoader<T> {

    public String mUrl;     // mUrl
    public ConcurrentHashMap<String, String> mLinkedHashMap;    // post 请求的 key value;
    public RequestBody mBody;   // post 请求的 body
    public List<String> mList;  // post 请求的 list
    public String mJson;    // post json 数据
    public Class<T> mCls;   // 返回的bean
    public int mExecuteFction;  // 根据状态执行方法
    private Callback<T> mCall;  //回调
    private long mDelayed = -1; //延迟时间回调
    private String mPath;   //文件,图片路径（单图）

    public DataLoader() {
    }

    /**
     * 请求网络 根据状态调用不同的方法
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        T t = null;
        if (NetUtils.hasNetwork(Program.getAppContext())) {
            switch (mExecuteFction) {
                case EXECUTE_POST_BODY:
                    t = httpsPostResponseBean(mUrl, mBody, mCls);
                    break;
                case EXECUTE_POST_JSON:
                    t = httpsPostJsonResponseBean(mUrl, mJson, mCls);
                    break;
                case EXECUTE_POST_MAP:
                    t = httpsPostResponseBean(mUrl, mLinkedHashMap, mCls);
                    break;
                case EXECUTE_POST_IMAGE_UPLOADINGS: {
                    final List<String> imageUrls = new ArrayList<>();
                    try {
                        for (int i = 0; i < mList.size(); i++) {
                            String imageUrl = mList.get(i);
                            String compressImage = Utils.compressImage(imageUrl, 720, 1080, 200);
                            imageUrls.add(compressImage);
                        }
                        t = (T) httpsExecutePostImages(mUrl, imageUrls);
                    } catch (Exception e) {
                        BaseDataBean error = new BaseDataBean();
                        error.code = -1;
                        error.message = Utils.getStringXml(R.string.update_image_hint);
                        t = (T) error;
                    }
                    try {
                        if (t instanceof BaseDataBean) {
                            if (((BaseDataBean) t).code == 0) {
                                FileUtils.deleteImageCacheDir();
                            }
                        }
                    } catch (Exception e) {
                    }
                    break;
                }
                case EXECUTE_POST_IMAGE_UPLOADING:
                    t = httpsExecutePostImage(mUrl, mPath, mCls);
                    break;
                case EXECUTE_GET_BODY:
                    t = httpsGetResponceBean(mUrl, mCls);
                    break;
                case EXECUTE_POST_IMAGE_UPLOADINGS_KEY: {
                    HashMap<String, String> imagesMap_key = new HashMap<>();
                    try {
                        for (int i = 0; i < mList.size(); i++) {
                            String imageUrl = mList.get(i);
                            String compressImage = Utils.compressImage(imageUrl, 720, 1080, 200);
                            imagesMap_key.put(imageUrl, compressImage);
                        }
                        t = (T) httpsExecutePostImagesKey(mUrl, imagesMap_key);
                    } catch (Exception e) {
                        BaseDataBean error = new BaseDataBean();
                        error.code = -1;
                        error.message = Utils.getStringXml(R.string.update_image_hint);
                        t = (T) error;
                    }
                    try {
                        if (t instanceof BaseDataBean) {
                            if (((BaseDataBean) t).code == 0) {
                                FileUtils.deleteImageCacheDir();
                            }
                        }
                    } catch (Exception e) {
                    }
                    break;
                }
                case EXECUTE_POST_IMAGE_UPLOADINGS_KEY_PHOTO_SUBSCRIPTION: {
                    HashMap<String, String> imagesMap_key = new HashMap<>();
                    try {
                        for (int i = 0; i < mList.size(); i++) {
                            String imageUrl = mList.get(i);
                            String compressImage = Utils.compressImage(imageUrl, 720, 1080, 200);
                            imagesMap_key.put(String.valueOf(i), compressImage);
                        }
                        t = (T) httpsExecutePostImagesKey(mUrl, imagesMap_key);
                    } catch (Exception e) {
                        BaseDataBean error = new BaseDataBean();
                        error.code = -1;
                        error.message = Utils.getStringXml(R.string.update_image_hint);
                        t = (T) error;
                    }
                    try {
                        if (t instanceof BaseDataBean) {
                            if (((BaseDataBean) t).code == 0) {
                                FileUtils.deleteImageCacheDir();
                            }
                        }
                    } catch (Exception e) {
                    }
                    break;
                }
                case EXECUTE_POST_FILE: {
                    List<String> imageUrls = new ArrayList<>();
                    try {
                        for (int i = 0; i < mList.size(); i++) {
                            String imageUrl = mList.get(i);
                            String compressImage = Utils.compressImage(imageUrl, 720, 1080, 200);
                            imageUrls.add(compressImage);
                        }
                        t = (T) httpsExecutePostFile(mUrl, mLinkedHashMap, imageUrls);
                    } catch (Exception e) {
                        BaseDataBean error = new BaseDataBean();
                        error.code = -1;
                        error.message = Utils.getStringXml(R.string.update_image_hint);
                        t = (T) error;
                    }
                    try {
                        if (t instanceof BaseDataBean) {
                            if (((BaseDataBean) t).code == 0) {
                                FileUtils.deleteImageCacheDir();
                            }
                        }
                    } catch (Exception e) {
                    }
                    break;
                }

                default:
                    throw new IllegalArgumentException("ExecuteAction not be empty");
            }
            // 先将数据打印出来
            if (mCall == null)
                throw new IllegalArgumentException("Callback can not be empty");
        }

        final T finalT = t;
        if (mDelayed != -1) {
            SystemClock.sleep(mDelayed);
        }

        // 给的bean需要继承 BaseDataBean
        Utils.post(new Runnable() {
            @Override
            public void run() {
                if (finalT != null) {
                    result(finalT);
                } else {
                    returnNetError();
                }
            }
        });
    }

    private void result(T finalT) {
        if (finalT instanceof BaseDataBean) {
            BaseDataBean data = (BaseDataBean) finalT;
            switch (data.code) {
                case 0:
                    mCall.onResult(finalT);
                    break;
                case -2:
                    // 未登录
                    break;
                default:
                    returnError(data);
                    break;
            }
        } else if (finalT instanceof Map) {
            mCall.onResult(finalT);
        } else if (finalT instanceof List) {
            mCall.onResult(finalT);
        }
    }

    private void returnNetError() {
        Error error = new Error();
        error.error = Utils.getStringXml(R.string.net_error);
        mCall.onError(error);
    }

    private void returnError(BaseDataBean finalT) {
        Error error = new Error(finalT.message);
        error.code = finalT.code;
        if (error.code == -1 && error.error.contains("Exception")) {
            error.error = Utils.getStringXml(R.string.system_exception);
        }
        mCall.onError(error);
    }

    //GET 请求 Bean
    public <T> T httpsGetResponceBean(String url, Class<T> cls) {
        String result;
        result = HttpLoader.getInstace().executeGet(url);
        if (!TextUtils.isEmpty(result)) {
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }


    // POST 请求 Bean
    public <T> T httpsPostResponseBean(String url, ConcurrentHashMap<String, String> map, Class<T> cls) {
        String result;
        result = HttpLoader.getInstace().executePostMap(url, map);
        if (!TextUtils.isEmpty(result)) {
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }


    //POST 请求 Bean
    public <T> T httpsPostResponseBean(String url, RequestBody request, Class<T> cls) {
        String result;
        result = HttpLoader.getInstace().executePostBody(url, request);
        if (!TextUtils.isEmpty(result)) {
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }


    public List<String> httpsExecutePostImages(String url, List<String> paths) {
        List<String> images_temp = HttpLoader.getInstace().executePostImages(url, paths);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < images_temp.size(); i++) {
            final DatasBean image = GsonTools.changeGsonToBean(images_temp.get(i), DatasBean.class);
            list.add(image.data);
        }
        return list;
    }

    public List<FileBean.DataBean> httpsExecutePostFile(String url, ConcurrentHashMap<String, String> map, List<String> paths) {
        List<String> images_temp = HttpLoader.getInstace().executePostImagesNewApi(url, map, paths);
        List<FileBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < images_temp.size(); i++) {
            final FileBean image = GsonTools.changeGsonToBean(images_temp.get(i), FileBean.class);
            list.add(image.getData());
        }
        return list;
    }


    public Map<String, String> httpsExecutePostImagesKey(String url, HashMap<String, String> paths) {
        Map<String, String> path = HttpLoader.getInstace().executePostImagesKey(url, paths);
        final HashMap<String, String> images = new HashMap<>();
        for (final Map.Entry<String, String> entry : path.entrySet()) {
            images.put(entry.getKey(), entry.getValue());
        }
        return images;
    }

    // POST Json数据 请求 Bean
    public <T> T httpsPostJsonResponseBean(String url, String json, Class<T> cls) {
        String result;
        result = HttpLoader.getInstace().executePostJson(url, json);

        if (!TextUtils.isEmpty(result)) {
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }

    // 单图上传（头像）
    public T httpsExecutePostImage(String url, String path, Class<T> cls) {
        String result;
        result = HttpLoader.getInstace().executePostImage(url, path);
        if (!TextUtils.isEmpty(result)) {
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }


    @Override
    public ILoader path(String path) {
        mPath = path;
        return this;
    }

    @Override
    public ILoader url(String url) {
        // 可以在这里对 url 进行过滤或者处理
        mUrl = url;
        return this;
    }

    @Override
    public ILoader list(List<String> list) {
        mList = list;
        return this;
    }


    @Override
    public ILoader map(ConcurrentHashMap<String, String> map) {
        mLinkedHashMap = map;
        return this;
    }

    @Override
    public ILoader cls(Class<T> cls) {
        mCls = cls;
        return this;
    }

    @Override
    public ILoader requestBody(RequestBody body) {
        mBody = body;
        return this;
    }

    @Override
    public ILoader json(String json) {
        mJson = json;
        return this;
    }

    @Override
    public ILoader executeFction(int fction) {
        mExecuteFction = fction;
        return this;
    }

    @Override
    public ILoader delayed(long time) {
        mDelayed = time;
        return this;
    }

    @Override
    public Thread callback(Callback<T> call) {
        mCall = call;
        return this;
    }
}
