package beetrack.com.news.mvp.model.netwotk;

import java.util.HashMap;
import java.util.List;

import beetrack.com.news.mvp.model.data.ResultModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Enny Querales
 */
public interface RestAPIService {

    @GET("top-headlines")
    Call<ResultModel> getArticles(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
