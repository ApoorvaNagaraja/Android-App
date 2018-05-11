package com.example.android.placessearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favorite.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorite extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView nofav;
    private RecyclerView fav_recyclerView;
    SharedPreferance shared_preference;
    List<Setter> favorites;



    private OnFragmentInteractionListener mListener;

    public Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorite newInstance(String param1, String param2) {
        Favorite fragment = new Favorite();
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        fav_recyclerView = (RecyclerView)view.findViewById(R.id.fav_recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        nofav = (TextView)view.findViewById(R.id.nofav);
        fav_recyclerView.setLayoutManager(mLinearLayoutManager);
        shared_preference = new SharedPreferance();
        favorites = shared_preference.getFavorites(getActivity());
        getTable();
        return view ;
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
    public void getTable()
    {
        if(favorites == null)
        {
            nofav.setText("No favorites");
            Log.d("inNOfavNUll","inNOnUll");
        }

        if(favorites!=null) {
            Log.d("favoriteLIst",favorites.toString());
            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getContext(),favorites);
            fav_recyclerView.setAdapter(favoriteAdapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferance shared_preference = new SharedPreferance();
        favorites = shared_preference.getFavorites(getActivity());
        if(favorites == null)
        {
            nofav.setText("No favorites");
        }
        if(favorites!=null) {
            getTable();
        }

    }
}
