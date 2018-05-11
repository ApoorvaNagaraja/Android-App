package com.example.android.placessearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


public class PlaceDetailsActivity extends AppCompatActivity implements Info.OnFragmentInteractionListener,
        Pictures.OnFragmentInteractionListener,
        Maps.OnFragmentInteractionListener,
        Reviews.OnFragmentInteractionListener
{
    private String placeID;
    private String name;
    private String address1;
    private String icon;
    private TextView nameEditor;
    private ImageView shareImage;
    private String url;
    private String address;
    private String weburl;
    private ImageView ivFavorite;
    SharedPreferance shared_preferance;
    String placeResponse;
    ProgressDialog progressDialog;
   // public List<Setter> mDetail = new ArrayList<Setter>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_place_details);
        shared_preferance=new SharedPreferance();
        placeID = getIntent().getStringExtra("placeID");
        name = getIntent().getStringExtra("name");
        address1 = getIntent().getStringExtra("address");
        icon = getIntent().getStringExtra("icon");
        final Setter setter= new Setter(icon,name,address1,placeID);
        //mDetail.add(setter);
        nameEditor = (TextView)findViewById(R.id.nameID);
        nameEditor.setText(name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ivFavorite =(ImageView)findViewById(R.id.ivFavorite);
        if(checkFav(placeID))
        {
            ivFavorite.setImageResource(R.drawable.white_full_heart);
        }
        else
        {
            ivFavorite.setImageResource(R.drawable.white_outline);
        }

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivFavorite.getDrawable().getConstantState()== getBaseContext().getResources().getDrawable( R.drawable.white_outline).getConstantState()) {
                    ivFavorite.setImageResource(R.drawable.white_full_heart);
                    Toast.makeText(view.getContext(), name+" was added to favorites", Toast.LENGTH_LONG).show();
                    shared_preferance.addFavorite(getBaseContext(),setter);
                } else  {
                    ivFavorite.setImageResource(R.drawable.white_outline);
                    Toast.makeText(view.getContext(), name+" was removed from favorites", Toast.LENGTH_LONG).show();
                    Log.d("setter",setter.toString());
                    shared_preferance.removeFavorite(getBaseContext(),setter);
                   // mDetail.remove(0);

                }
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching details");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String APIKey ="AIzaSyDptlHYXtPhfVcziZpdoNTrs1Iq0ALEDYo";
        url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeID+"&key="+APIKey;
        Log.d("placeURL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        placeResponse = response;
                        Log.d("placeRes",placeResponse);
                        progressDialog.hide();

                        try {

                            JSONObject object = new JSONObject(placeResponse);
                            JSONObject getObject = object.getJSONObject("result");
                            address = getObject.getString("formatted_address");
                            weburl =getObject.getString("website");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        shareImage = (ImageView)findViewById(R.id.share);
                        shareImage.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                intent.setData(Uri.parse("https://twitter.com/intent/tweet?text=Check out " +name+" located at "+address+" Website:"+"&url="+weburl+"&hashtags="+"TravelAndEntertainmentSearch"));
                                startActivity(intent);
                            }
                        });

                        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager1);

                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout1);

                        TextView tabOne = (TextView) LayoutInflater.from(PlaceDetailsActivity.this).inflate(R.layout.cutom_layout_info, null);
                        tabOne.setText(" INFO");
                        tabOne.setTextColor(Color.WHITE);
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info, 0, 0, 0);
                        tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne));

                        TextView tabTwo = (TextView) LayoutInflater.from(PlaceDetailsActivity.this).inflate(R.layout.cutom_layout_pics, null);
                        tabTwo.setText(" PHOTOS");
                        tabTwo.setTextColor(Color.WHITE);
                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pics, 0, 0, 0);
                        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTwo));

                        TextView tabThree = (TextView) LayoutInflater.from(PlaceDetailsActivity.this).inflate(R.layout.cutom_layout_maps, null);
                        tabThree.setText(" MAP");
                        tabThree.setTextColor(Color.WHITE);
                        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.maps, 0, 0, 0);
                        tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));

                        TextView tabFour = (TextView) LayoutInflater.from(PlaceDetailsActivity.this).inflate(R.layout.cutom_layout_reviews, null);
                        tabFour.setText(" REVIEWS");
                        tabFour.setTextColor(Color.WHITE);
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.reviews, 0, 0, 0);
                        tabLayout.addTab(tabLayout.newTab().setCustomView(tabFour));


                        Bundle bundle = new Bundle();
                        bundle.putString("placeID", placeResponse);
                        Log.d("placeRes",placeResponse);

                        final DetailsAdapter adapter = new DetailsAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),bundle);
                        viewPager.setAdapter(adapter);
                        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                Log.d("pos",""+tab.getPosition());
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", "error");
                    }
                });

        queue.add(stringRequest);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public boolean checkFav(String placeID) {

        boolean flag = false;
        List<Setter> favorites = shared_preferance.getFavorites(this);
        if (favorites != null) {
            for (Setter sets : favorites) {
                if ((sets.getmPlaceID()).compareTo(placeID)==0) {
                    flag = true;
                    break;
                }
            }
        }
        Log.d("in checkFav","position"+flag);
        return flag;
    }



}
