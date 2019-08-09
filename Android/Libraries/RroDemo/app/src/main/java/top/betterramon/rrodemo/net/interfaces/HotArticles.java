package top.betterramon.rrodemo.net.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import top.betterramon.rrodemo.net.beans.ArticlesSubject;

/**
 * Created by Ramon Lee on 2019/8/9.
 */
public interface HotArticles {
    @GET("/article/list/{page}/json")
    Call<ArticlesSubject> getHotArticles(@Path("page") int page);
}
