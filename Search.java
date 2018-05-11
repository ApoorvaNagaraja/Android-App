package com.example.android.placessearch;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.location.FusedLocationProviderClient;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONObject;
//import com.google.android.gms.tasks.OnSuccessListener;

//import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText keyword;
    private AutoCompleteTextView location;
    private TextInputLayout location_layout;
    private TextInputLayout keyword_layout;
    private Button s_button;
    private AutoCompleteTextView t;
    private RadioButton rb;
    private RadioButton rb2;
    private  TextView textView;
    private EditText distance;
    private ProgressDialog progressDialog;
    public JSONArray dataResponse;
    private Spinner spinner;
    private Button button2;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(32, -117), new LatLng(44, -67));
    //private FusedLocationProviderClient client;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Search() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

        View view =inflater .inflate(R.layout.fragment_search, container, false);
        keyword=(EditText)view.findViewById(R.id.keyword);
        keyword_layout=(TextInputLayout)view.findViewById(R.id.keyword_layout);
        s_button=(Button)view.findViewById(R.id.s_button);
        t = (AutoCompleteTextView)view.findViewById(R.id.location);
        rb = (RadioButton) view.findViewById(R.id.radioButton);
        rb2 = (RadioButton) view.findViewById(R.id.radioButton2);
        location=(AutoCompleteTextView)view.findViewById(R.id.location);
        location_layout=(TextInputLayout)view.findViewById(R.id.location_layout);
        spinner  = (Spinner)view.findViewById(R.id.spinner1);
        distance =(EditText)view.findViewById(R.id.distance);
        button2 =(Button) view.findViewById(R.id.button2);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching results");

        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(), mGoogleApiClient,LAT_LNG_BOUNDS, null);
        location.setAdapter(mPlaceAutocompleteAdapter);









        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
                if(submitForm()) {
                    getPlaces();
                }

            }
        });



        t.setEnabled(rb2.isChecked());

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                t.setEnabled(isChecked);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();

            }
        });
        // Inflate the layout for this fragment
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

    private boolean submitForm()
    {
        if(keyword.getText().toString().trim().isEmpty() &&(t.isEnabled()==false))
        {
            keyword_layout.setErrorEnabled(true);
            keyword_layout.setError(" Please enter a mandatory field");
            Toast.makeText(getContext(), "Please fix all fields with errors", Toast.LENGTH_LONG).show();
            return false;
        }

        if(location.getText().toString().trim().isEmpty() && (t.isEnabled() == true) && (!keyword.getText().toString().trim().isEmpty()))
        {
            location_layout.setErrorEnabled(true);
            location_layout.setError(" Please enter a mandatory field");
            Toast.makeText(getContext(), "Please fix all fields with errors", Toast.LENGTH_LONG).show();
            return false;
        }

        if(location.getText().toString().trim().isEmpty() && (t.isEnabled() == true) && keyword.getText().toString().trim().isEmpty() )
        {
            keyword_layout.setErrorEnabled(true);
            keyword_layout.setError(" Please enter a mandatory field");
            location_layout.setErrorEnabled(true);
            location_layout.setError(" Please enter a mandatory field");
            Toast.makeText(getContext(), "Please fix all fields with errors", Toast.LENGTH_LONG).show();
            return false;
        }

        location_layout.setErrorEnabled(false);
        keyword_layout.setErrorEnabled(false);
        return true;
    }


 public String conversionCategory()
 {
     String spinnerStr;
     if(spinner.getSelectedItem().toString()== "Amusement Park")
     {
         spinnerStr="amusement_park";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Art Gallery")
     {
         spinnerStr="art_gallery";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Beauty Salon")
     {
         spinnerStr="beauty_salon";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Bowling Alley")
     {
         spinnerStr="bowling_alley";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Bus Station")
     {
         spinnerStr="bus_station";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Car Rental")
     {
         spinnerStr="car_rental";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Movie Theatre")
     {
         spinnerStr="movie_theater";
         return spinnerStr;
     } else if(spinner.getSelectedItem().toString()== "Night Club")
     {
         spinnerStr="night_club";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Shopping Mall")
     {
         spinnerStr="shopping_mall";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Subway Station")
     {
         spinnerStr="subway_station";
         return spinnerStr;
     } else if(spinner.getSelectedItem().toString()== "Taxi Stand")
     {
         spinnerStr="taxi_stand";
         return spinnerStr;
     } else if(spinner.getSelectedItem().toString()== "Train Station")
     {
         spinnerStr="train_station";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Transit Station")
     {
         spinnerStr="transit_station";
         return spinnerStr;
     }
     else if(spinner.getSelectedItem().toString()== "Travel Agency")
     {
         spinnerStr="travel_agency";
         return spinnerStr;
     }
     else
     return spinner.getSelectedItem().toString();
 }
  public void getPlaces() {

      progressDialog.show();
      RequestQueue queue = Volley.newRequestQueue(getContext());
      if(distance.getText().toString().trim().length() == 0)
      {

          distance.setText("10");
          Log.d("",distance.getText().toString());
      }

      Log.d("",distance.getText().toString());
      double Newdistance = Double.parseDouble(distance.getText().toString());
      Newdistance = Newdistance * 1609.34;
      String url;

      if(location.isEnabled()==true)
      {
           url = "http://webassignment-env.us-east-2.elasticbeanstalk.com/location?keyword="+keyword.getText().toString()+"&category="+conversionCategory()+"&distance="+Newdistance+"&address="+location.getText().toString();
      }
      else {
           url = "http://webassignment-env.us-east-2.elasticbeanstalk.com/place?keyword=" + keyword.getText().toString() + "&category=" + conversionCategory() + "&distance=" + Newdistance + "&p1=" + MainActivity.latitude + "&p2=" + MainActivity.longitude;
      }
      JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
              (url, new Response.Listener<JSONArray>() {

                  @Override
                  public void onResponse(JSONArray response) {
                      // mTextView.setText("Response: " + response.toString());
                      //dataResponse = response;
                      Log.d("", response.toString());
                      progressDialog.hide();
                      Intent intent=new Intent(getActivity(),SecondActivity.class);
                      intent.putExtra("data",response.toString());
                      startActivity(intent);
                  }
              }, new Response.ErrorListener() {

                  @Override
                  public void onErrorResponse(VolleyError error) {

                      Log.d("","error");
                      progressDialog.hide();
                      Toast.makeText(getContext(), "Slow internet connection", Toast.LENGTH_LONG).show();
                  }
              });

           jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          queue.add(jsonObjectRequest);



  }

  public void clearForm()
  {
     if(keyword!=null)
     {
        keyword.setText("");
     }
     if(distance!=null)
     {
         distance.setText("");
     }
      if(location!=null)
      {
          location.setText("");
      }
      if(rb.isChecked()==false)
      {
          rb.setChecked(true);
          rb2.setChecked(false);
          location.setEnabled(false);
      }
      location_layout.setError("");
      keyword_layout.setError("");
      spinner.setSelection(0);
  }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
