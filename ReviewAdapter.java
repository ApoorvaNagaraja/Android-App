package com.example.android.placessearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.Float.parseFloat;

/**
 * Created by as on 4/25/2018.
 */

public class ReviewAdapter extends  RecyclerView.Adapter<ReviewViewHolder> {

    private List<reviewSetter> mReview;
    private Context mContext;



    public ReviewAdapter(Context mContext, List mReview) {
        Log.d("in review","in review adap");

        this.mReview = mReview;
        this.mContext = mContext;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recycler,
                parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder,final int position) {

        Picasso.with(mContext).load(mReview.get(position).getmprodilePic()).into(holder.prodilePic);
        holder.rev_name.setText(mReview.get(position).getmrev_name());
        holder.comment.setText(mReview.get(position).getmcomment());
        holder.datetime.setText(mReview.get(position).getmdatetime());
        holder.ratingBar2.setRating(parseFloat(mReview.get(position).getmratingBar2()));
        holder.rev_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =mReview.get(position).getmurl() ;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("mReviewsize",""+mReview.size());
        return mReview.size();
    }
}


class ReviewViewHolder extends RecyclerView.ViewHolder
{
    ImageView prodilePic;
    TextView rev_name;
    RatingBar ratingBar2;
    TextView datetime;
    TextView comment;
    TextView url;
    RelativeLayout rev_relativeLayout;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        prodilePic = itemView.findViewById(R.id.prodilePic);
        rev_name = itemView.findViewById(R.id.rev_name);
        ratingBar2 = itemView.findViewById(R.id.ratingBar2);
        datetime = itemView.findViewById(R.id.datetime);
        comment = itemView.findViewById(R.id.comment);
        url = itemView.findViewById(R.id.url);
        rev_relativeLayout = itemView.findViewById(R.id.rev_relativeLayout);
    }
}