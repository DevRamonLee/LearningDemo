package ramon.better.com.retrofitdemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limeng on 2019/3/29.
 */

public class NetUtil {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
