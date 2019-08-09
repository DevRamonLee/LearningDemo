package ramon.better.com.retrofitdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ramon.better.com.retrofitdemo.R;
import ramon.better.com.retrofitdemo.beans.OfficialAccounts;
import ramon.better.com.retrofitdemo.beans.Translation;
import ramon.better.com.retrofitdemo.net.GetRequest_Interface;
import ramon.better.com.retrofitdemo.net.PostRequest_Interface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request();
        postRequest();
    }

    // 例 1： 获取 wanandroid 公众号列表
    private void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) // 设置使用Gson解析(记得加入依赖)
                .build();

        // 创建网络请求接口
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送的请求进行封装
        Call<OfficialAccounts> call = request.getOfficialAccounts();

        // 异步发送网络请求
        call.enqueue(new Callback<OfficialAccounts>() {
            @Override
            public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
                // 处理返回的数据
                Log.i("RamonLee", response.body().toString());
            }

            @Override
            public void onFailure(Call<OfficialAccounts> call, Throwable t) {

            }
        });
    }

    // 例 2：使用 post 方式使用有道 api 获取翻译
    private void postRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        // 对发送请求进行封装(设置需要翻译的内容)
        Call<Translation> call = request.getCall("Tomorrow is Friday.");

        // 发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {

            // 请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 处理返回的数据结果：输出翻译的内容
                Log.i("RamonLee", response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
