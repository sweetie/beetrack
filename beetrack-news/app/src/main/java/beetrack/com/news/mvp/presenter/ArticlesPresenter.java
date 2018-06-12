package beetrack.com.news.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericPresenter;
import beetrack.com.news.mvp.common.global.Enums;
import beetrack.com.news.mvp.common.interfaces.Listener.OnNetworkResponseListener;
import beetrack.com.news.mvp.model.GlobalModel;
import beetrack.com.news.mvp.model.netwotk.NetworkRequest;
import beetrack.com.news.mvp.model.request.QueryArticle;
import beetrack.com.news.mvp.view.activities.WebViewActivity;


/**
 * Created by Enny Querales
 */
public class ArticlesPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsFragment, MVP.RequiredPresenterMethods, OnNetworkResponseListener {

    /**
     * Attributes
     */
    private WeakReference<MVP.RequiredFragmentMethods> view;
    private Context context;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(MVP.RequiredFragmentMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);

        // Initialize context
        context = this.view.get().getActivityContext();
    }

    @Override
    public <T> void executeNetworkRequest() {
        NetworkRequest request = new QueryArticle<>(this);
        request.performNetworkRequest();
        view.get().displayDialog(Enums.DialogType.TIME_UNDETERMINED);
    }

    @Override
    public <T> void processResponse(T response) {
        view.get().updateView(response);
    }

    @Override
    public <T> void processFailure(T response) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }
}
