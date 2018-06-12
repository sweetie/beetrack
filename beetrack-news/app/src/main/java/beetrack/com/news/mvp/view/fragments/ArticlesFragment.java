package beetrack.com.news.mvp.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beetrack.com.news.R;
import beetrack.com.news.mvp.Beetrack;
import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericFragment;
import beetrack.com.news.mvp.common.interfaces.Listener;
import beetrack.com.news.mvp.common.interfaces.Observer;
import beetrack.com.news.mvp.common.interfaces.Subject;
import beetrack.com.news.mvp.model.DBHelper;
import beetrack.com.news.mvp.model.data.ArticleModel;
import beetrack.com.news.mvp.model.data.ResultModel;
import beetrack.com.news.mvp.presenter.ArticlesPresenter;
import beetrack.com.news.mvp.view.activities.MainActivity;
import beetrack.com.news.mvp.view.adapter.ArticleListAdapter;
import beetrack.com.news.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Enny Querales
 */
public class ArticlesFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, MVP.ProvidedPresenterMethodsFragment, ArticlesPresenter>
        implements MVP.RequiredFragmentMethods, Subject, Listener.OnFavoriteSelectedListener {

    /**
     * Attributes
     */
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private ArticleListAdapter articleListAdapter;
    private MainActivity parentActivity;
    private DBHelper helper;
    private List<ArticleModel> articles = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();;

    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container          view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_articles, container, false);

        // Initialize parent activity
        parentActivity = (MainActivity) getActivity();

        // Initialize retained fragment state
        isRetainedFragment = false;

        // Instantiate the presenter
        super.onCreate(ArticlesPresenter.class, this);

        // Create Helper
        helper = OpenHelperManager.getHelper(parentActivity, DBHelper.class);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return linearLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        recyclerView = (RecyclerView) linearLayout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        try {
            Beetrack.setArticles(helper.getArticleDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setDialog(linearLayout.findViewById(R.id.dialog));
        getPresenter().executeNetworkRequest();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void updateView(T data) {
        ResultModel resultModel = (ResultModel) data;
        articles = resultModel.getArticles();
        articleListAdapter = new ArticleListAdapter(getContext(), articles, false);
        recyclerView.setAdapter(articleListAdapter);
        articleListAdapter.setOnFavoriteSelectedListener(this);
    }

    @Override
    public void register(final Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unregister(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public <T> void notifyObservers(T model) {
        for (final Observer observer : observers) {
            observer.update(model);
        }
    }

    @Override
    public <T> void onSelected(T model) {
        notifyObservers(model);
    }
}
