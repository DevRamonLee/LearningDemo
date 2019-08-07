package top.betterramon.okhttpdemo2.bean;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class UploadImageKey extends BaseDataBean{
    public UploadImageKey.Data data;
    public static class Data {
        public String url;
        public String key;
    }
}
