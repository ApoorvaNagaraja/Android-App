package com.example.android.placessearch;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Maps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Maps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Maps extends Fragment implements GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback   {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public  Spinner travel;
    private String travelMode;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AutoCompleteTextView from;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private String placeID;
    private String lat;
    private String lng;
    private String name;
    private GoogleMap mMap;
    private String origin;
    private String destination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(32, -117), new LatLng(44, -67));
    //private FusedLocationProviderClient client;

    private OnFragmentInteractionListener mListener;

    public Maps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Maps.
     */
    // TODO: Rename and change types and number of parameters
    public static Maps newInstance(String param1, String param2) {
        Maps fragment = new Maps();
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
        View view =inflater.inflate(R.layout.fragment_maps, container, false);
        placeID = getArguments().getString("placeID");
        travel = (Spinner)view.findViewById(R.id.travelSpinner);




        try {
            String responseObj = placeID;
            JSONObject object = new JSONObject(responseObj);
            JSONObject getObject = object.getJSONObject("result");
            name = getObject.getString("name");
            JSONObject getGeo = getObject.getJSONObject("geometry");
            JSONObject getLoc = getGeo.getJSONObject("location");
            lat = getLoc.getString("lat");
            lng = getLoc.getString("lng");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        from = (AutoCompleteTextView)view.findViewById(R.id.from_location);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(), mGoogleApiClient,LAT_LNG_BOUNDS, null);
        from.setAdapter(mPlaceAutocompleteAdapter);

        from.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             findDistance();

            }
        });

        travel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               findDistance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });



      SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng placesLat = new LatLng(parseDouble(lat) ,parseDouble(lng));
        Marker marker = mMap.addMarker(new MarkerOptions().position(placesLat)
              .title(name));
        marker.showInfoWindow();
        originMarkers.add(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placesLat, 15));
    }



    public void onDirectionFinderStart() {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                Log.d("remove","remove Marker");
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }

    }


    public void onDirectionFinderSuccess(List<Route> route) {

        Log.d("onDirectionFinder","onDirectionFinder");

        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route routes : route) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(routes.startLocation,15));


           originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(routes.startAddress)
                    .position(routes.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(routes.endAddress)
                    .position(routes.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < routes.points.size(); i++)
                polylineOptions.add(routes.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
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


    private void findDistance() {

        Log.d("find distance","find dis");
        String origin = from.getText().toString();
        String destination =lat+","+lng;
        Log.d("length",""+origin.length());
        if(origin.length() != 0) {
            onDirectionFinderStart();
            travelMode = travel.getSelectedItem().toString().toLowerCase();
            //new DirectionFinder(this, origin, destination, travelMode);
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination +"&mode="+travelMode+"&key=AIzaSyC8nAjVCvsijuD_uu6Bl6j1K5daj2co8ro";
            Log.d("travelURl",url);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<Route> routes = new ArrayList<Route>();
                                JSONObject jsonData = new JSONObject(response);
                                JSONArray jsonRoutes = jsonData.getJSONArray("routes");
                                for (int i = 0; i < jsonRoutes.length(); i++) {
                                    JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                                    Route route = new Route();

                                    JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                                    JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                                    JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                                    JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                                    JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");


                                    route.endAddress = jsonLeg.getString("end_address");
                                    route.startAddress = jsonLeg.getString("start_address");
                                    route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                                    route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
                                    route.points = decodePolyLine(overview_polylineJson.getString("points"));
                                    routes.add(route);
                                }
                                onDirectionFinderSuccess(routes);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }},new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("errorResponse", "error");
                }
            });

            queue.add(stringRequest);

        }
    }



    private List<LatLng> decodePolyLine(final String poly) {
        List<LatLng> decoded = PolyUtil.decode(poly);
        return decoded;
    }




}
