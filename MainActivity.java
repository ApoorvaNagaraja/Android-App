package com.example.android.placessearch;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements Search.OnFragmentInteractionListener,Favorite.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    public static String latitude;
    public static String longitude;
    private FusedLocationProviderClient client;
    private int[] tabIcons = {
            R.drawable.searchdraw,
            R.drawable.white_full_heart
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = LocationServices.getFusedLocationProviderClient(this);

         getLocation();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
       /* tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]).setText("SEARCH"));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]).setText("FAVORITES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
        tabOne.setText("SEARCH");
        tabOne.setTextColor(Color.WHITE);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchdraw, 0, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne));

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_layout_fav, null);
        tabTwo.setText("FAVORITES");
        tabTwo.setTextColor(Color.WHITE);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.white_full_heart, 0, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTwo));


        final com.example.android.placessearch.PagerAdapter adapter = new com.example.android.placessearch.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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




    public void getLocation()
    {
        getPermission();
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            return ;
        }
        client.getLastLocation().addOnSuccessListener(this , new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {

                   // Log.d(TAG, location.toString());
                   // Log.d(location.getLongitude();
                    double lati  = location.getLatitude();
                    double longi  = location.getLongitude();
                    latitude = String.valueOf(lati);
                    longitude =String.valueOf(longi);
                    Log.d("latit",latitude);
                    Log.d("longit",longitude);


                }
            }
        });
    }

    private void getPermission()
    {
        ActivityCompat.requestPermissions(this ,new String[]{ACCESS_FINE_LOCATION},1);
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
