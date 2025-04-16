package ramon.better.com.retrofitdemo.net;

import ramon.better.com.retrofitdemo.beans.OfficialAccounts;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ramon Lee on 2019/8/8.
 */
public interface GetRequest_Interface {

    @GET("wxarticle/chapters/json")
    Call<OfficialAccounts> getOfficialAccounts();
}
