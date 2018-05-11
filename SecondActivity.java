package com.example.android.placessearch;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.*;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private String mName;
    private String mAddress;
    private String mIcon;
    private String mplaceID;
    private ImageView src;
    private TextView noTable;
    public List<Setter> mData = new ArrayList<Setter>();
    private int curPage=0;
    private RecyclerView mRecyclerView;
    Button nextBtn,prevBtn;
    int totalPages ;
    int itemsRemaining;
    public static int len;
    Pagination p;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(SecondActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        nextBtn = (Button)findViewById(R.id.nextBtn);
        prevBtn =(Button)findViewById(R.id.prevBtn);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


            try {
                    String obj = getIntent().getStringExtra("data");
                    JSONArray json = new JSONArray(obj);
                    len = json.length();
                    Log.d("len","len"+len);
                    if(len==0) {
                        noTable=(TextView)findViewById(R.id.noTable);
                        noTable.setText("No Results");
                        nextBtn.setVisibility(View.GONE);
                        prevBtn.setVisibility(View.GONE);
                    }
                    else
                    {
                    for (int i = 0; i < json.length(); i++)
                    {
                        mName = json.getJSONObject(i).getString("name");
                        mIcon = json.getJSONObject(i).getString("icon");
                        mAddress = json.getJSONObject(i).getString("vicinity");
                        mplaceID = json.getJSONObject(i).getString("place_id");
                        Setter setter= new Setter(mIcon,mName,mAddress,mplaceID);
                        mData.add(setter);
                    }

                p= new Pagination();
                totalPages = len/20;
                itemsRemaining = len % 20;
                TableAdapter mTableAdapter = new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData));
                mRecyclerView.setAdapter(mTableAdapter);
                changeButtons();


                nextBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        curPage += 1;
                        Log.d("show","nextPage");
                        mRecyclerView.setAdapter( new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData)));
                        changeButtons();
                    }
                });

                prevBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        curPage -= 1;
                        mRecyclerView.setAdapter( new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData)));
                        changeButtons();
                    }
                });
            } }
            catch (JSONException e) {

            e.printStackTrace();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void changeButtons()
    {

        Log.d("curPage",""+curPage);
        Log.d("totalPage",""+totalPages);
        Log.d("itemsRemaining",""+itemsRemaining);
        if(curPage == totalPages && curPage !=0)
        {
            Log.d("case 1","case 1");
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        }
        else if(curPage== totalPages-1 && itemsRemaining ==0)
        {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        }
        else if(curPage == 0 && curPage != totalPages)
        {
            Log.d("case 2","case 2");
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(false);
        }
        else if(curPage >= 1 && curPage < totalPages)
        {
            Log.d("case 3","case 3");
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }
        else if(curPage == totalPages && curPage ==0)
        {
            Log.d("case 4","case 4");
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(false);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            String obj = getIntent().getStringExtra("data");
            JSONArray json = new JSONArray(obj);
            len = json.length();
            Log.d("len","len"+len);
            if(len==0) {
                noTable=(TextView)findViewById(R.id.noTable);
                noTable.setText("No Results");
                nextBtn.setVisibility(View.GONE);
                prevBtn.setVisibility(View.GONE);
            }
            else
            {
                for (int i = 0; i < json.length(); i++)
                {
                    mName = json.getJSONObject(i).getString("name");
                    mIcon = json.getJSONObject(i).getString("icon");
                    mAddress = json.getJSONObject(i).getString("vicinity");
                    mplaceID = json.getJSONObject(i).getString("place_id");
                    Setter setter= new Setter(mIcon,mName,mAddress,mplaceID);
                    mData.add(setter);
                }

                p= new Pagination();
                totalPages = len/20;
                itemsRemaining = len % 20;
                TableAdapter mTableAdapter = new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData));
                mRecyclerView.setAdapter(mTableAdapter);
                changeButtons();


                nextBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        curPage += 1;
                        Log.d("show","nextPage");
                        mRecyclerView.setAdapter( new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData)));
                        changeButtons();
                    }
                });

                prevBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        curPage -= 1;
                        mRecyclerView.setAdapter( new TableAdapter(SecondActivity.this,p.generatePage(curPage,mData)));
                        changeButtons();
                    }
                });
            } }
        catch (JSONException e) {

            e.printStackTrace();
        }


    }
}
