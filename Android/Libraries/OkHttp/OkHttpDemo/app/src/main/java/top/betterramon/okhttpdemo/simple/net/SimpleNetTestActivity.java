package top.betterramon.okhttpdemo.simple.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.betterramon.okhttpdemo.R;

public class SimpleNetTestActivity extends AppCompatActivity {

    private TextView getResultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_net_test);
        getResultTv = findViewById(R.id.get_result);
        findViewById(R.id.get_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://write.blog.csdn.net/postlist/0/0/enabled/1";
                OkHttpUtils.getInstance().get(url, new BaseCallBack<String>() {
                    @Override
                    public void onBeforeRequest(Request request) {

                    }

                    @Override
                    public void onSuccess(Response response, String s) {
                        getResultTv.setText(s);
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }

                    @Override
                    public void onFailure(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {

                    }

                    @Override
                    public void onTokenError(Response response, int code) {

                    }
                });
            }
        });
    }
}
