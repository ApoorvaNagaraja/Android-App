package com.example.android.placessearch;

import android.util.Log;

/**
 * Created by as on 4/17/2018.
 */

public class Setter {

    private String mName;
    private String mAddress;
    private String mIcon;
    private String mPlaceID;

    public Setter (String mIcon, String mName, String mAddress,String mPlaceID) {
        Log.d("","in setter");
        this.mIcon = mIcon;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPlaceID=mPlaceID;

    }
    public String getmIcon()
    {
        return mIcon;
    }
    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPlaceID() {
        return mPlaceID;
    }

    public String toString() {
        return "Name: '" + this.mName + "', icon: '" + this.mIcon + "', ratingBar2: '" + this.mAddress + "', comment: '" + this.mPlaceID + "'";
    }

    @Override
    public boolean equals(Object obj)
    {
        Setter setter = (Setter)obj;
        boolean flag = false;
        if ((setter.getmPlaceID()).compareTo(this.mPlaceID)==0)
        {
            flag = true;
        }
        return flag;
    }


}
