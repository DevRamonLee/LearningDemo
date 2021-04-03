package top.betterramon.rrodemo.net;

import com.jkyeo.basicparamsinterceptor.BasicParamsInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.betterramon.rrodemo.api.ApiConfig;

/**
 * Created by Ramon Lee on 2019/8/12.
 * 统一生成接口实例的管理类
 */
public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;      // 默认超时时间
    private static final int DEFAULT_READ_TIME_OUT = 10;    // 默认读取超时时间
    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        // 创建 OkhttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);     // 连接超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);        // 读操作超时时间

        // 添加公共参数拦截器
        HttpCommonInterceptor basicParamsInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445")
                .build();

        builder.addInterceptor(basicParamsInterceptor);


        // 创建 Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取 RetrofitServiceManager 实例
     */

    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
