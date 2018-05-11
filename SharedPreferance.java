package com.example.android.placessearch;

/**
 * Created by as on 4/26/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferance {

    public SharedPreferance() {
        super();



    }


    public void saveFavorites(Context context, List<Setter> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences("app",
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        Log.d("remove_fav",""+jsonFavorites);

        editor.putString("favorites", jsonFavorites);
       // editor.clear();
        editor.commit();
    }

    public void addFavorite(Context context, Setter setter) {
        List<Setter> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Setter>();
        favorites.add(setter);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Setter setter) {
        ArrayList<Setter> favorites = getFavorites(context);
        if (favorites != null) {
            Log.d("remove_fav_setter",setter.toString());
            favorites.remove(setter);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Setter> getFavorites(Context context) {
        SharedPreferences settings;
        List<Setter> favorites;

        settings = context.getSharedPreferences("app",
                Context.MODE_PRIVATE);

        if (settings.contains("favorites")) {
            String jsonFavorites = settings.getString("favorites", null);
            Gson gson = new Gson();
            Setter[] favoriteItems = gson.fromJson(jsonFavorites,
                    Setter[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Setter>(favorites);
        } else
            return null;

        return (ArrayList<Setter>) favorites;
    }




}
