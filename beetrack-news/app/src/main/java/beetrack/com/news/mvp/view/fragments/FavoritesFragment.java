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
import beetrack.com.news.mvp.common.interfaces.Observer;
import beetrack.com.news.mvp.model.DBHelper;
import beetrack.com.news.mvp.model.data.ArticleModel;
import beetrack.com.news.mvp.presenter.EmptyPresenterFragment;
import beetrack.com.news.mvp.view.activities.MainActivity;
import beetrack.com.news.mvp.view.adapter.ArticleListAdapter;
import beetrack.com.news.mvp.view.decorator.DividerItemDecoration;

/**
 * Created by Enny Querales
 */
public class FavoritesFragment
        extends GenericFragment<MVP.RequiredFragmentMethods, Void, EmptyPresenterFragment>
        implements MVP.RequiredFragmentMethods, Observer {

    /**
     * Attributes
     */
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private MainActivity parentActivity;
    private DBHelper helper;
    private ArticleListAdapter articleListAdapter;
    private List<ArticleModel> articles = new ArrayList<>();

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
        linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_favorites, container, false);

        // Initialize parent activity
        parentActivity = (MainActivity) getActivity();

        // Initialize retained fragment state
        isRetainedFragment = false;

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
            articles = helper.getArticleDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        articleListAdapter = new ArticleListAdapter(getContext(), articles, true);
        recyclerView.setAdapter(articleListAdapter);
    }

    @Override
    public <T> void updateView(T data) {

    }

    @Override
    public <T> void update(T model) {
        try {
            helper.createOrUpdate((ArticleModel) model);
            if (!articles.contains(model)) {
                Beetrack.getArticles().add((ArticleModel) model);
                articles.add((ArticleModel) model);
                articleListAdapter.notifyDataSetChanged();
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
