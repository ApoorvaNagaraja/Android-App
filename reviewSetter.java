package com.example.android.placessearch;

import android.util.Log;

/**
 * Created by as on 4/25/2018.
 */

public class reviewSetter {


    private String prodilePic;
    private String rev_name;
    private String ratingBar2;
    private String datetime;
    private String comment;
    private String url;

    public reviewSetter (String prodilePic, String rev_name, String ratingBar2,String datetime,String comment,String url ) {
        this.prodilePic = prodilePic;
        this.rev_name = rev_name;
        this.ratingBar2 = ratingBar2;
        this.datetime=datetime;
        this.comment=comment;
        this.url=url;

    }
    public String getmprodilePic()
    {
        return prodilePic;
    }
    public String getmrev_name() {
        return rev_name;
    }

    public String getmratingBar2() {
        return ratingBar2;
    }

    public String getmdatetime() {
        return datetime;
    }

    public String getmcomment()
    {
        return comment;
    }
    public String getmurl()
    {
        return url;
    }

    public String toString() {
        return "Name: '" + this.rev_name + "', datetime: '" + this.datetime + "', ratingBar2: '" + this.ratingBar2 + "', comment: '" + this.comment + "'";
    }
}
