package com.example.android.placessearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reviews.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String placeID;
    private String prodilePic;
    private String rev_name;
    private String ratingBar2;
    private String datetime;
    private String comment;
    private String url;
    private TextView noReviews;
    private Spinner order;
    private String order_str;
    private Spinner Reviews;
    private String reviews_str;
    private RecyclerView review_recycler;
    public List<reviewSetter> mReview = new ArrayList<reviewSetter>();
    public List<reviewSetter> mReviewOrg = new ArrayList<reviewSetter>();
    public List<reviewSetter> empty = new ArrayList<reviewSetter>();
    private String country;
    private String state;
    private String postal_code;
    private String locality;
    private String address1;
    private String name;
    private String address;
    private String url2;
    private String y_prodilePic;
    private String y_rev_name;
    private String y_ratingBar2;
    private String y_datetime;
    private String y_comment;
    private String y_url;
    private List<reviewSetter> y_mReview = new ArrayList<reviewSetter>();
    private List<reviewSetter> y_mReviewOrg = new ArrayList<reviewSetter>();



    private OnFragmentInteractionListener mListener;

    public Reviews() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reviews.
     */
    // TODO: Rename and change types and number of parameters
    public static Reviews newInstance(String param1, String param2) {
        Reviews fragment = new Reviews();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reviews, container, false);
        review_recycler=(RecyclerView)view.findViewById(R.id.review_recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        review_recycler.setLayoutManager(mLinearLayoutManager);
        placeID = getArguments().getString("placeID");
        order = (Spinner)view.findViewById(R.id.order);
        Reviews=(Spinner)view.findViewById(R.id.Reviews);
        noReviews=(TextView)view.findViewById(R.id.noReviews);
        try{
            String responseObj = placeID;
            JSONObject object = new JSONObject(responseObj);
            JSONObject getObject = object.getJSONObject("result");
            name = getObject.getString("name");
            address=getObject.getString("formatted_address");
            String[] res = address.split(",");
            address1=res[0];
            JSONArray formatAdd =getObject.getJSONArray("address_components");
            for(int j=0;j<formatAdd.length();j++)
            {
                String type = formatAdd.getJSONObject(j).getJSONArray("types").get(0).toString();
                Log.d("typeMain",type);
                if(type.compareTo("country")==0) {
                   country = formatAdd.getJSONObject(j).getString("short_name");
                }
                if(type.compareTo("postal_code")==0)
                {
                    postal_code=formatAdd.getJSONObject(j).getString("short_name");
                }

                if(type.compareTo("administrative_area_level_1")==0)
                {
                    state=formatAdd.getJSONObject(j).getString("short_name");

                }
                if(type.compareTo("locality")==0)
                {
                    locality=formatAdd.getJSONObject(j).getString("short_name");
                }
            }
            JSONArray reviewArray = getObject.getJSONArray("reviews");
            for (int i = 0; i < reviewArray.length(); i++) {
                    Log.d("in else","in else");
                    prodilePic = reviewArray.getJSONObject(i).getString("profile_photo_url");
                    Log.d("profile pic", prodilePic);
                    rev_name = reviewArray.getJSONObject(i).getString("author_name");
                    datetime = reviewArray.getJSONObject(i).getString("time");
                    Date date =new Date(parseLong(datetime)*1000);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String rev_date = format.format(date);
                    comment = reviewArray.getJSONObject(i).getString("text");
                    url = reviewArray.getJSONObject(i).getString("author_url");
                    ratingBar2 = reviewArray.getJSONObject(i).getString("rating");
                    reviewSetter reviewset= new reviewSetter(prodilePic,rev_name,ratingBar2,rev_date,comment,url);
                    mReview.add(reviewset);
                    mReviewOrg.add(reviewset);
                }
                ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                 review_recycler.setAdapter(mReviewAdapter);
            }
        catch (JSONException e) {


            noReviews.setText("No Reviews");
            e.printStackTrace();
        }



        /* order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                order_str = order.getSelectedItem().toString();
                Log.d("order_str",order_str);
                if(order_str.compareTo("Highest Rating")==0)
                {
                    getGoogleHighestRating();
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                    review_recycler.setAdapter(mReviewAdapter);
                }
                if(order_str.compareTo("Lowest Rating")==0)
                {
                    getGoogleLowestRating();
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                    review_recycler.setAdapter(mReviewAdapter);
                }
                if(order_str.compareTo("Most Recent")==0)
                {
                    getGoogleMostRecentRating();
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                    review_recycler.setAdapter(mReviewAdapter);

                }
                if(order_str.compareTo("Least Recent")==0)
                {
                    getGoogleLeastRecentRating();
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                    review_recycler.setAdapter(mReviewAdapter);

                }
                if(order_str.compareTo("Default Order")==0)
                {

                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),mReviewOrg);
                    review_recycler.setAdapter(mReviewAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });*/

        Reviews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reviews_str=Reviews.getSelectedItem().toString();
                if(reviews_str.compareTo("Yelp Reviews")==0)
                {
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),empty);
                    review_recycler.setAdapter(mReviewAdapter);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    url2= "http://webassignment-env.us-east-2.elasticbeanstalk.com/yelp?name="+name+"&locality="+locality+"&postal_code="+postal_code+"&country="+country+"&state="+state+"&address1="+address1;
                    Log.d("yelpURL",url2);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,url2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    try {

                                        JSONArray json= new JSONArray(response);
                                        for(int k =0;k<json.length();k++)
                                        {
                                            y_url = json.getJSONObject(k).getString("url");
                                            y_rev_name=json.getJSONObject(k).getJSONObject("user").getString("name");
                                            y_prodilePic=json.getJSONObject(k).getJSONObject("user").getString("image_url");
                                            y_comment=json.getJSONObject(k).getString("text");
                                            y_datetime=json.getJSONObject(k).getString("time_created");
                                            y_ratingBar2=json.getJSONObject(k).getString("rating");
                                            reviewSetter reviewset= new reviewSetter(y_prodilePic,y_rev_name,y_ratingBar2,y_datetime,y_comment,y_url);
                                            y_mReview.add(reviewset);
                                            y_mReviewOrg.add(reviewset);


                                        }
                                        ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                                        review_recycler.setAdapter(mReviewAdapter);

                                    }catch (JSONException e) {
                                        noReviews.setText("No Reviews");
                                        e.printStackTrace();
                                    }
                                }
                                },
                                 new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("errorResponse", "error");
                                    }
                                });

        queue.add(stringRequest);
                    if(order_str.compareTo("Highest Rating")==0 )
                    {
                        getYelpHighestRating();
                        ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                        review_recycler.setAdapter(mReviewAdapter1);
                    }
                    if(order_str.compareTo("Lowest Rating")==0 )
                    {
                        getYelpLowestRating();
                        ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                        review_recycler.setAdapter(mReviewAdapter1);
                    }
                    if(order_str.compareTo("Most Recent")==0 )
                    {
                        getYelpMostRecentRating();
                        ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                        review_recycler.setAdapter(mReviewAdapter1);

                    }
                    if(order_str.compareTo("Least Recent")==0 )
                    {
                        getYelpLeastRecentRating();
                        ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                        review_recycler.setAdapter(mReviewAdapter1);

                    }
                    if(order_str.compareTo("Default Order")==0 )
                    {

                        ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),y_mReviewOrg);
                        review_recycler.setAdapter(mReviewAdapter1);
                    }


                }

                order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        order_str = order.getSelectedItem().toString();
                        Log.d("order_str",order_str);
                        if(order_str.compareTo("Highest Rating")==0 && reviews_str.compareTo("Yelp Reviews")==0 )
                        {
                            getYelpHighestRating();
                            ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                            review_recycler.setAdapter(mReviewAdapter);
                        }
                        if(order_str.compareTo("Lowest Rating")==0 && reviews_str.compareTo("Yelp Reviews")==0 )
                        {
                            getYelpLowestRating();
                            ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                            review_recycler.setAdapter(mReviewAdapter);
                        }
                        if(order_str.compareTo("Most Recent")==0 && reviews_str.compareTo("Yelp Reviews")==0)
                        {
                            getYelpMostRecentRating();
                            ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                            review_recycler.setAdapter(mReviewAdapter);

                        }
                        if(order_str.compareTo("Least Recent")==0 && reviews_str.compareTo("Yelp Reviews")==0)
                        {
                            getYelpLeastRecentRating();
                            ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReview);
                            review_recycler.setAdapter(mReviewAdapter);

                        }
                        if(order_str.compareTo("Default Order")==0 && reviews_str.compareTo("Yelp Reviews")==0)
                        {

                            ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),y_mReviewOrg);
                            review_recycler.setAdapter(mReviewAdapter);
                        }

                        if(order_str.compareTo("Highest Rating")==0 && reviews_str.compareTo("Google Reviews")==0)
                        {
                            getGoogleHighestRating();
                            ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                            review_recycler.setAdapter(mReviewAdapter1);
                        }
                        if(order_str.compareTo("Lowest Rating")==0 && reviews_str.compareTo("Google Reviews")==0)
                        {
                            getGoogleLowestRating();
                            ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                            review_recycler.setAdapter(mReviewAdapter1);
                        }
                        if(order_str.compareTo("Most Recent")==0 && reviews_str.compareTo("Google Reviews")==0)
                        {
                            getGoogleMostRecentRating();
                            ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                            review_recycler.setAdapter(mReviewAdapter1);

                        }
                        if(order_str.compareTo("Least Recent")==0 && reviews_str.compareTo("Google Reviews")==0)
                        {
                            getGoogleLeastRecentRating();
                            ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                            review_recycler.setAdapter(mReviewAdapter1);

                        }
                        if(order_str.compareTo("Default Order")==0 && reviews_str.compareTo("Google Reviews")==0)
                        {

                            ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReviewOrg);
                            review_recycler.setAdapter(mReviewAdapter1);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                if(reviews_str.compareTo("Google Reviews")==0)
                {
                    ReviewAdapter mReviewAdapter = new ReviewAdapter(getContext().getApplicationContext(),empty);
                    review_recycler.setAdapter(mReviewAdapter);
                    order_str = order.getSelectedItem().toString();
                            Log.d("order_str",order_str);
                            if(order_str.compareTo("Highest Rating")==0)
                            {
                                getGoogleHighestRating();
                                ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                                review_recycler.setAdapter(mReviewAdapter1);
                            }
                            if(order_str.compareTo("Lowest Rating")==0)
                            {
                                getGoogleLowestRating();
                                ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                                review_recycler.setAdapter(mReviewAdapter1);
                            }
                            if(order_str.compareTo("Most Recent")==0)
                            {
                                getGoogleMostRecentRating();
                                ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                                review_recycler.setAdapter(mReviewAdapter1);

                            }
                            if(order_str.compareTo("Least Recent")==0)
                            {
                                getGoogleLeastRecentRating();
                                ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReview);
                                review_recycler.setAdapter(mReviewAdapter1);

                            }
                            if(order_str.compareTo("Default Order")==0)
                            {

                                ReviewAdapter mReviewAdapter1 = new ReviewAdapter(getContext().getApplicationContext(),mReviewOrg);
                                review_recycler.setAdapter(mReviewAdapter1);

                            }
                        }
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getGoogleHighestRating() {
        Collections.sort(mReview, new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                //Log.d("compare",o1.toString());
                //Log.d("compare2", o2.toString());
                if(parseFloat(o1.getmratingBar2())==parseFloat((o2.getmratingBar2())))
                {
                    return 0;
                }
                return parseFloat(o1.getmratingBar2())>parseFloat((o2.getmratingBar2()))?-1:1;

            }
        });
    }

    public void getGoogleLowestRating() {
        Collections.sort(mReview, new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                //Log.d("compare",o1.toString());
                //Log.d("compare2", o2.toString());
                if(parseFloat(o1.getmratingBar2())==parseFloat((o2.getmratingBar2())))
                {
                    return 0;
                }
                return parseFloat(o1.getmratingBar2())<parseFloat((o2.getmratingBar2()))?-1:1;

            }
        });
    }



    public void getGoogleMostRecentRating() {
        Collections.sort(mReview, Collections.<reviewSetter>reverseOrder(new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                if (o1.getmdatetime() == null || o2.getmdatetime() == null)
                    return 0;
                return o1.getmdatetime().compareTo(o2.getmdatetime());

            }
        }));
    }

    public void getGoogleLeastRecentRating(){
      Collections.sort(mReview, new Comparator<reviewSetter>() {
        public int compare(reviewSetter o1, reviewSetter o2) {
            if (o1.getmdatetime() == null || o2.getmdatetime() == null)
                return 0;
            return o1.getmdatetime().compareTo(o2.getmdatetime());

        }
    });
}

    public void getYelpHighestRating() {
        Collections.sort(y_mReview, new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                //Log.d("compare",o1.toString());
                //Log.d("compare2", o2.toString());
                if(parseFloat(o1.getmratingBar2())==parseFloat((o2.getmratingBar2())))
                {
                    return 0;
                }
                return parseFloat(o1.getmratingBar2())>parseFloat((o2.getmratingBar2()))?-1:1;

            }
        });
    }

    public void getYelpLowestRating() {
        Collections.sort(y_mReview, new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                //Log.d("compare",o1.toString());
                //Log.d("compare2", o2.toString());
                if(parseFloat(o1.getmratingBar2())==parseFloat((o2.getmratingBar2())))
                {
                    return 0;
                }
                return parseFloat(o1.getmratingBar2())<parseFloat((o2.getmratingBar2()))?-1:1;

            }
        });
    }


    public void getYelpMostRecentRating() {
        Collections.sort(y_mReview, Collections.<reviewSetter>reverseOrder(new Comparator<reviewSetter>() {
            public int compare(reviewSetter o1, reviewSetter o2) {
                if (o1.getmdatetime() == null || o2.getmdatetime() == null)
                    return 0;
                return o1.getmdatetime().compareTo(o2.getmdatetime());

            }
        }));
    }

    public void getYelpLeastRecentRating() {
        Collections.sort(y_mReview, new Comparator<reviewSetter>() {

            public int compare(reviewSetter o1, reviewSetter o2) {
                if (o1.getmdatetime() == null || o2.getmdatetime() == null)
                    return 0;
                return o1.getmdatetime().compareTo(o2.getmdatetime());
            }
        });
    }


}
