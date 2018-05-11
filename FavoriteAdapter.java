package com.example.android.placessearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by as on 4/25/2018.
 */

public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteViewHolder>  {

    private List<Setter> mData;
    private Context mContext;
    SharedPreferance shared_preferance;
    List<Setter> fav_favorites;

    public FavoriteAdapter(Context mContext, List<Setter> mData) {

        this.mData = mData;
        this.mContext = mContext;
        shared_preferance=new SharedPreferance();
    }
    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_recycler,
                parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder,final int position) {

        Picasso.with(mContext).load(mData.get(position).getmIcon()).into(holder.favIcon);
        holder.fav_name.setText(mData.get(position).getmName());
        holder.fav_address.setText(mData.get(position).getmAddress());
        holder.fav_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("placeID",mData.get(position).getmPlaceID());
                intent.putExtra("name",mData.get(position).getmName());
                context.startActivity(intent);
            }
        });
        holder.fav_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mData.get(position).getmName()+" was removed from favorites", Toast.LENGTH_LONG).show();
                shared_preferance.removeFavorite(mContext,mData.get(position));
                mData.remove(position);
                notifyItemRemoved(position);
                Log.d("getItemsize",""+mData.size());
                notifyItemRangeChanged(position, getItemCount());
                if(getItemCount()==0)
                {
                        Log.d("inNofav","inNo");
                        holder.nofav1.setText("No favorites");
                }

                //notifyDataSetChanged();



            }
        });

    }

    @Override
    public int getItemCount() {
        if(mData!=null) {
            return mData.size();
        }
        else
            return 0;
    }
}


class FavoriteViewHolder extends RecyclerView.ViewHolder
{
    ImageView favIcon;
    TextView fav_name;
    TextView fav_address;
    ImageView fav_Favorite;
    RelativeLayout fav_relativeLayout;
    TextView nofav1;


    public FavoriteViewHolder(View itemView) {
        super(itemView);
        favIcon = itemView.findViewById(R.id.favIcon);
        fav_name = itemView.findViewById(R.id.fav_name);
        fav_address = itemView.findViewById(R.id.fav_address);
        fav_Favorite = itemView.findViewById(R.id.fav_Favorite);
        fav_relativeLayout = itemView.findViewById(R.id.fav_relativeLayout);
        nofav1= itemView.findViewById(R.id.nofav1);
    }

}
