package com.example.android.placessearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by as on 4/10/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int nNoOfTabs;

    public PagerAdapter(FragmentManager fm,int NumberOfTabs)
    {
        super(fm);
        this.nNoOfTabs= NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Search search =new Search();
                return search;
            case 1:
                Favorite favorite =new Favorite();
                return favorite;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return nNoOfTabs;
    }
}
