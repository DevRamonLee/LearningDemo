package top.betterramon.okhttpdemo2.net;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import top.betterramon.okhttpdemo2.Program;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
enum OkHttpFactory {

    INSTANCE;

    public static final int TIME_OUT = 5;
    private final OkHttpClient okHttpClient;
    private InputStream[] certificates;

    OkHttpFactory() {
        //缓存目录
        Cache cache = new Cache(Program.getAppContext().getCacheDir(), 10 * 1024 * 1024);

        ClearableCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),
                new SharedPrefsCookiePersistor(Program.getAppContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, null, null);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CacheInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)     // 连接超时
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)       // 写入超时
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)        // 读取超时
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(cookieJar)
                .build();
    }

    public OkHttpClient getOkHttpClient(InputStream... certificates) {
        this.certificates = certificates;
        return okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return getOkHttpClient(new InputStream[]{});
    }
}
