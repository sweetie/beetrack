package beetrack.com.news.mvp.model;

import android.content.Context;

import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericModel;


/**
  * Created by Enny Querales
 */
public class GlobalModel
        extends GenericModel
        implements MVP.ProvidedModelMethods {


    public GlobalModel() {
        // no-op
    }

    public GlobalModel(Context context) {
        super(context);
    }
}
