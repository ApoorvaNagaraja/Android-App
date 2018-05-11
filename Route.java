package com.example.android.placessearch;

/**
 * Created by as on 4/25/2018.
 */

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;
    public List<LatLng> points;
}
