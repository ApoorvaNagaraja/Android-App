package com.example.android.placessearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by as on 4/16/2018.
 */

public class TableAdapter extends  RecyclerView.Adapter<TableViewHolder> {

    private List<Setter> mData;
    private Context mContext;
    private String placeID;
    private boolean flag = false;
    SharedPreferance shared_preferance;



    public TableAdapter(Context mContext, List<Setter> mData) {

        this.mData = mData;
        this.mContext = mContext;
        shared_preferance=new SharedPreferance();
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_table,
                parent, false);

        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, final int position)
    {


        Picasso.with(mContext).load(mData.get(position).getmIcon()).into(holder.mIcon);
        holder.mName.setText(mData.get(position).getmName());
        holder.mAddress.setText(mData.get(position).getmAddress());
        if(checkFav(mData.get(position)))
        {
            holder.mFavorite.setImageResource(R.drawable.heart_red);
        }
        else
        {
            holder.mFavorite.setImageResource(R.drawable.heart_blackoutline);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("placeID",mData.get(position).getmPlaceID());
                intent.putExtra("name",mData.get(position).getmName());
                intent.putExtra("icon",mData.get(position).getmIcon());
                intent.putExtra("address",mData.get(position).getmAddress());
                context.startActivity(intent);
            }
        });
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mFavorite.getDrawable().getConstantState()== mContext.getResources().getDrawable( R.drawable.heart_blackoutline).getConstantState()) {
                    holder.mFavorite.setImageResource(R.drawable.heart_red);
                    Toast.makeText(mContext, mData.get(position).getmName()+" was added to favorites", Toast.LENGTH_LONG).show();
                    shared_preferance.addFavorite(mContext,mData.get(position));
                } else  {
                    holder.mFavorite.setImageResource(R.drawable.heart_blackoutline);
                    Toast.makeText(mContext, mData.get(position).getmName()+" was removed from favorites", Toast.LENGTH_LONG).show();
                    shared_preferance.removeFavorite(mContext,mData.get(position));
                    notifyItemChanged(position);
                    notifyDataSetChanged();

                }
            }
        });


}

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    public boolean checkFav(Setter checkProduct) {

        boolean flag = false;
        List<Setter> favorites = shared_preferance.getFavorites(mContext);
        if (favorites != null) {
            for (Setter sets : favorites) {
                Log.d("favListCehcksets",sets.toString());
                Log.d("blah",checkProduct.toString());
                if (sets.toString().compareTo(checkProduct.toString())==0) {
                    flag = true;
                    break;
                }
            }
        }
        Log.d("in checkFav","position"+flag);
        return flag;
    }







}


class TableViewHolder extends RecyclerView.ViewHolder
{
    ImageView mIcon;
    TextView mName;
    TextView mAddress;
    ImageView mFavorite;
    RelativeLayout relativeLayout;


    public TableViewHolder(View itemView) {
        super(itemView);

        mIcon = itemView.findViewById(R.id.tvIcon);
        mName = itemView.findViewById(R.id.name);
        mAddress = itemView.findViewById(R.id.address);
        mFavorite = itemView.findViewById(R.id.ivFavorite);
        relativeLayout = itemView.findViewById(R.id.relativeLayout);

    }


}