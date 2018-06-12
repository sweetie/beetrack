package beetrack.com.news.mvp.model.request;

import beetrack.com.news.mvp.common.global.Constants;
import beetrack.com.news.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import beetrack.com.news.mvp.common.utilities.Logger;
import beetrack.com.news.mvp.model.data.ResultModel;
import beetrack.com.news.mvp.model.netwotk.Client;
import beetrack.com.news.mvp.model.netwotk.NetworkRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enny Querales
 */

public class QueryArticle<M>
        implements NetworkRequest {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;
    private OnNetworkResponseListener listener = null;


    public QueryArticle(OnNetworkResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public void performNetworkRequest() {
        Call<ResultModel> call = Client.getRestAPIService()
                .getArticles(Constants.COUNTRY,Constants.API_KEY);

        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    if (listener != null)
                        listener.processResponse(response.body());
                } else {
                    logger.log("Something went wrong! \n" + response.message());
                    listener.processFailure("Something went wrong! \n" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable throwable) {
                try {
                    logger.log(throwable.getCause().toString());
                    listener.processFailure("Something went wrong! \n" + throwable.getCause().toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
