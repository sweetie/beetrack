package beetrack.com.news.mvp.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Enny Querales
 */

public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    /**
     * Attributes
     */
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, int title) {
        fragments.add(fragment);
        titles.add(context.getString(title));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
