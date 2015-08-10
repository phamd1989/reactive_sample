package com.univtop.univtop.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.univtop.univtop.fragments.CustomFeedFragment;
import com.univtop.univtop.fragments.FaqFeedFragment;
import com.univtop.univtop.fragments.TrendingFeedFragment;
/**
 * Created by duncapham on 8/9/15.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;
    private final int PAGE_COUNT = tabTitles.length;

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return TrendingFeedFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FaqFeedFragment.newInstance(2);
            default:
                return CustomFeedFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
