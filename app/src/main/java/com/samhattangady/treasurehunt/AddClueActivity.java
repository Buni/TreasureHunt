package com.samhattangady.treasurehunt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddClueActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker marker;
    private LatLng mapCenter;
    private boolean markerIsSet;
    private Button setMarkerButton;
    private float userZoom;
    private static final int PLACE_PICKER_REQUEST = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("OnMapReady", "Loading map.");
        setContentView(R.layout.activity_add_clue);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        ViewGroup.LayoutParams mapParams = mapFragment.getView().getLayoutParams();
        mapParams.height = Resources.getSystem().getDisplayMetrics().heightPixels/3;
        mapFragment.getView().setLayoutParams(mapParams);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Button searchButton = (Button) findViewById(R.id.search_hunt_button);
        setMarkerButton = (Button) findViewById(R.id.set_marker_button);
        setMarkerButton.setText(R.string.set_marker_button);

        float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        userZoom = 13;
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Default map center is Bangalore. If location is found, then cam is moved in OnConnected
        mapCenter = new LatLng(12.9716, 77.5946);

        marker = mMap.addMarker(new MarkerOptions().position(mapCenter));
        markerIsSet = false;

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (!markerIsSet) {
                    marker.setPosition(mMap.getCameraPosition().target);
                }
                userZoom = mMap.getCameraPosition().zoom;
            }
        });
    }

    public void setMarker(View view) {
        if (markerIsSet) {
            setMarkerButton.setText(R.string.set_marker_button);
            marker.setPosition(mMap.getCameraPosition().target);
        }
        else {
            setMarkerButton.setText(R.string.move_marker_button);
        }
        markerIsSet = !markerIsSet;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            mapCenter = new LatLng(location.getLatitude(), location.getLongitude());
        }
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                                                                .target(mapCenter)
                                                                .zoom(userZoom)
                                                                .build()), 1, null);
        marker.setPosition(mapCenter);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void openPlacePicker (View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(new LatLngBounds(mMap.getCameraPosition().target, mMap.getCameraPosition().target));
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlacePicker.getPlace(this, data);
                marker.setPosition(selectedPlace.getLatLng());
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                                                                        .target(marker.getPosition())
                                                                        .zoom(userZoom)
                                                                        .build()), 1, null);
                setMarkerButton.setText(selectedPlace.getAddress());
            }
        }
    }
}
