package beetrack.com.news.mvp.presenter;

import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericPresenter;
import beetrack.com.news.mvp.model.GlobalModel;

/**
 * Created by Enny Querales
 */
public class EmptyPresenterFragment
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsFragment, MVP.RequiredPresenterMethods {

    @Override
    public void onCreate(MVP.RequiredFragmentMethods view) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return null;
    }
}
