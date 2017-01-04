package com.samhattangady.treasurehunt;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class AddClueActivity extends FragmentActivity implements  OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("OnMapReady", "Loading map.");
        setContentView(R.layout.activity_add_clue);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        ViewGroup.LayoutParams mapParams = mapFragment.getView().getLayoutParams();
        mapParams.height = 500;
        mapFragment.getView().setLayoutParams(mapParams);
        mapFragment.getMapAsync(this);


        ImageView imageView = (ImageView) findViewById(R.id.image_clue);
        Picasso.with(imageView.getContext())
                .load("https://www.drupal.org/files/project-images/Stamen%20Watercolor.png")
                .into(imageView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.v("OnMapReady", "Map is ready apparently.");
        // Add a marker in Bangalore, and move the camera.
        LatLng bangalore = new LatLng(12.9716, 77.5946);
        mMap.addMarker(new MarkerOptions().position(bangalore).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bangalore, 10));
    }
}
