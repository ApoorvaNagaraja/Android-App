package com.example.android.placessearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Info.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Info extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String url;
    private String placeID;
    private String address;
    private String priceLevel;
    private String googleUrl;
    private String phone;
    private String rating;
    private String weburl;
    private TextView addressText;
    private TextView  phonetext;
    private TextView priceText;
    private TextView pageURL;
    private TextView webURL;
    private RatingBar ratingbar;
    private OnFragmentInteractionListener mListener;

    public Info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Info.
     */
    // TODO: Rename and change types and number of parameters
    public static Info newInstance(String param1, String param2) {
        Info fragment = new Info();
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
        View view =inflater.inflate(R.layout.fragment_info, container, false);
        PlaceDetailsActivity placeDetailsActivity =new PlaceDetailsActivity();
        placeID = getArguments().getString("placeID");
        addressText = (TextView)view.findViewById(R.id.addressText);
        phonetext=(TextView)view.findViewById(R.id.phonetext) ;
        priceText=(TextView)view.findViewById(R.id.priceText) ;
        pageURL=(TextView)view.findViewById(R.id.pageURL) ;
        webURL=(TextView)view.findViewById(R.id.webURL) ;
        ratingbar=(RatingBar)view.findViewById(R.id.ratingBar);

        try{
        String responseObj = placeID;
        JSONObject object = new JSONObject(responseObj);
        JSONObject getObject = object.getJSONObject("result");
        address = getObject.getString("formatted_address");
        Log.d("Placeaddress",address);
        addressText.setText(address);
        phone = getObject.getString("international_phone_number");
        phonetext.setText(phone);
        priceLevel = getObject.getString("price_level");
        String dollar="";
        for(int i=1;i<= parseInt(priceLevel);i++)
        {
            dollar += "$";
        }
        priceText.setText(dollar);
        rating = getObject.getString("rating");
        ratingbar.setRating(parseFloat(rating));
        googleUrl =getObject.getString("url");
        pageURL.setText(googleUrl);
        weburl =getObject.getString("website");
        webURL.setText(weburl);
    }
                        catch (JSONException e) {
        e.printStackTrace();
    }
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


}
