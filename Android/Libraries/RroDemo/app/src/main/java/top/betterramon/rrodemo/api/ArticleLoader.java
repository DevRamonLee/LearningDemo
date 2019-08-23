package top.betterramon.rrodemo.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import top.betterramon.rrodemo.beans.ArticlesBean;
import top.betterramon.rrodemo.net.BaseResponse;
import top.betterramon.rrodemo.net.ObjectLoader;
import top.betterramon.rrodemo.net.PayLoad;
import top.betterramon.rrodemo.net.RetrofitServiceManager;

/**
 * Created by Ramon Lee on 2019/8/12.
 */
public class ArticleLoader extends ObjectLoader {
    private ArticleService mArticleService;

    public ArticleLoader() {
        mArticleService = RetrofitServiceManager.getInstance().create(ArticleService.class);
    }

    public Observable<ArticlesBean> getArticles(int page) {
        return observe(mArticleService.getHotArticles(page))
                .map(new PayLoad<ArticlesBean>());
    }


    public interface ArticleService {
        // 获取 wanandroid 首页文章列表
        @GET("/article/list/{page}/json")
        Observable<BaseResponse<ArticlesBean>> getHotArticles(@Path("page") int page);
    }
}
