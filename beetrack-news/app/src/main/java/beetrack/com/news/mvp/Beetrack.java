package beetrack.com.news.mvp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import beetrack.com.news.mvp.model.data.ArticleModel;


/**
 * Created by Enny Querales.
 */
public class Beetrack extends Application {

    public static final String TAG = Beetrack.class.getSimpleName();
    private static Beetrack mInstance;
    private static List<ArticleModel> articles = new ArrayList<>();

    public static synchronized Beetrack getInstance() {
        return mInstance;
    }

    public static List<ArticleModel> getArticles() {
        return articles;
    }

    public static void setArticles(List<ArticleModel> articles) {
        Beetrack.articles = articles;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
