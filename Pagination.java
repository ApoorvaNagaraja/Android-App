package com.example.android.placessearch;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 4/18/2018.
 */

public class Pagination {
    public static final int NUM_ITEMS= SecondActivity.len;
    public static final int ITEMS_PAGE =20;
    public static final int ITEMS_REMAINING= NUM_ITEMS % ITEMS_PAGE;
    public static final int LAST_PAGE = NUM_ITEMS / ITEMS_PAGE;


    public ArrayList<Setter> generatePage(int curPage, List<Setter> mData)
    {
        int start = curPage * ITEMS_PAGE ;
        int dataPerPage =ITEMS_PAGE;


        ArrayList<Setter> pageData = new ArrayList<>();

        if((curPage == LAST_PAGE) && (ITEMS_REMAINING >0))
        {
            for(int i=start;i<start+ITEMS_REMAINING;i++ )
            {
                Setter getData= mData.get(i);
                pageData.add(getData);
            }
        }
        else
        {
            for(int i=start;i<(start+dataPerPage);i++ )
            {
                Setter getData= mData.get(i);
                pageData.add(getData);
            }
        }

        return pageData;

    }

}
