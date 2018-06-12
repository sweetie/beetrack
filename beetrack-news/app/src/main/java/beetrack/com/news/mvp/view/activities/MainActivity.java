package beetrack.com.news.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;

import beetrack.com.news.R;
import beetrack.com.news.mvp.MVP;
import beetrack.com.news.mvp.common.generic.GenericActivity;
import beetrack.com.news.mvp.common.interfaces.Listener.ExecutorListener;
import beetrack.com.news.mvp.presenter.MainActivityPresenter;
import beetrack.com.news.mvp.view.adapter.ViewPagerAdapter;
import beetrack.com.news.mvp.view.custom.CustomTabLayout;
import beetrack.com.news.mvp.view.fragments.ArticlesFragment;
import beetrack.com.news.mvp.view.fragments.FavoritesFragment;

/**
 * Created by Enny Querales
 */
public class MainActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, MainActivityPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener, ExecutorListener {

    /**
     * Attributes
     */
    private ViewPager viewPager;
    private Toolbar toolbar;
    private static boolean activityStarted;
    private CustomTabLayout tabLayout;
    private ViewPagerAdapter adapter;

    /**
     * Vector drawable support
     */
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (activityStarted && getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) != 0) {
            finish();
            return;
        }

        activityStarted = true;

        setContentView(R.layout.activity_main);

        // Instantiate the presenter
        super.onCreate(MainActivityPresenter.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }


    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, false);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setViewPager();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout = (CustomTabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Specifies which fragments will be contained on the {@param viewpager} and sets its adapter.
     */
    private void setViewPager() {
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        ArticlesFragment fragment1 = new ArticlesFragment();
        FavoritesFragment fragment2 = new FavoritesFragment();
        adapter.addFragment(fragment1, R.string.articles_label);
        adapter.addFragment(fragment2, R.string.articles_favorite_label);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        fragment1.register(fragment2);
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        // no-op
    }

    @Override
    public void execute(String name) {
    }


}
