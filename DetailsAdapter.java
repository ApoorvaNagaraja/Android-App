package com.example.android.placessearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


/**
 * Created by as on 4/20/2018.
 */

public class DetailsAdapter extends FragmentPagerAdapter {

    int nNoOfTabs;
    Bundle placeID;

    public DetailsAdapter(FragmentManager fm, int NumberOfTabs, Bundle bundle)
    {
        super(fm);
        this.nNoOfTabs= NumberOfTabs;
        this.placeID = bundle;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Info info =new Info();
                info.setArguments(this.placeID);
                return info;
            case 1:
                Pictures pictures =new Pictures();
                pictures.setArguments(this.placeID);
                return pictures;
            case 2:
                Maps maps =new Maps();
                maps.setArguments(this.placeID);
                return maps;
            case 3:
                Reviews reviews =new Reviews();
                reviews.setArguments(this.placeID);
                return reviews;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return nNoOfTabs;
    }
}

