package top.betterramon.rrodemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.betterramon.rrodemo.R;
import top.betterramon.rrodemo.adapters.ArticlesAdapter;
import top.betterramon.rrodemo.net.beans.ArticlesSubject;
import top.betterramon.rrodemo.net.interfaces.HotArticles;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://www.wanandroid.com";

    private RecyclerView articlesRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndLoadData();
    }

    private void initViewAndLoadData() {

        articlesRecycler = findViewById(R.id.articles_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        articlesRecycler.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotArticles service = retrofit.create(HotArticles.class);
        Call<ArticlesSubject> articlesCall = service.getHotArticles(0);
        articlesCall.enqueue(new Callback<ArticlesSubject>() {
            @Override
            public void onResponse(Call<ArticlesSubject> call, Response<ArticlesSubject> response) {
                ArticlesAdapter articlesAdapter = new ArticlesAdapter(MainActivity.this, response.body());
                articlesRecycler.setAdapter(articlesAdapter);
            }

            @Override
            public void onFailure(Call<ArticlesSubject> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
