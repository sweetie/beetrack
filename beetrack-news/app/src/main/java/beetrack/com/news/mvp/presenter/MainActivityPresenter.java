package beetrack.com.news.mvp.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericPresenter;
import beetrack.com.news.mvp.model.GlobalModel;

/**
 * Created by Enny Querales
 */
public class MainActivityPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsActivity, MVP.RequiredPresenterMethods {

    /**
     * Attributes
     */
    private WeakReference<MVP.RequiredActivityMethods> view;
    private Context context;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(MVP.RequiredActivityMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);

        context = this.view.get().getActivityContext();
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param viewId Indicates the id of the button pressed by the user
     */
    @Override
    public void handleClick(int viewId) {
        // no-op
    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }
}
