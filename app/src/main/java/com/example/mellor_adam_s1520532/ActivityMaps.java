package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Sets the layout for the view to the activity maps xml file
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

//Displays the map at the given coordinates zoomed in to level 15
    @Override
    public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;

        LatLng GCU = new LatLng( 55.866433, -4.250261);

        myMap.addMarker(new MarkerOptions().position(GCU).title("Marker at Glasgow Caledonian University"));

        myMap.moveCamera(CameraUpdateFactory.newLatLng(GCU));

        myMap.setMinZoomPreference(15.0f);

        myMap.setMaxZoomPreference(20.0f);

    }

}

