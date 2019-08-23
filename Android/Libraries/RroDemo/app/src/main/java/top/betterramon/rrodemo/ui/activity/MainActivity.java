package top.betterramon.rrodemo.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rx.functions.Action1;
import top.betterramon.rrodemo.R;
import top.betterramon.rrodemo.api.ArticleLoader;
import top.betterramon.rrodemo.beans.ArticlesBean;
import top.betterramon.rrodemo.net.Fault;
import top.betterramon.rrodemo.ui.adapter.ArticlesAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView articlesRecycler;
    private ArticleLoader mArticleLoader;

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

        mArticleLoader = new ArticleLoader();
        mArticleLoader.getArticles(0).subscribe(new Action1<ArticlesBean>() {
                    @Override
                    public void call(ArticlesBean articlesBean) {
                        ArticlesAdapter articlesAdapter = new ArticlesAdapter(MainActivity.this, articlesBean.getDatas());
                        articlesRecycler.setAdapter(articlesAdapter);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+throwable.getMessage());
                        Fault fault = (Fault) throwable;
                        if(fault.getErrorCode() == 1) {
                            // 进行不同类型的错误处理
                        } else if (fault.getErrorCode() == 2) {

                        }
                    }
                }
        );
    }
}
