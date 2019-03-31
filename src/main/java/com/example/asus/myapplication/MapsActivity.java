package com.example.asus.myapplication;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String eventName;
    double lng, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventName = getIntent().getStringExtra("name");
        String locName = getIntent().getStringExtra("location");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(locName, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        lng = address.getLongitude();
        lat = address.getLatitude();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in FCT and move the camera
        LatLng fct = new LatLng(38.661233, -9.204910);
        mMap.addMarker(new MarkerOptions().position(fct).title("You are here"));

        BitmapDrawable bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.flag);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap icon = Bitmap.createScaledBitmap(b, 105, 105, false);

        //Add a marker in Event Location
        LatLng event = new LatLng (lat, lng);
         mMap.addMarker(new MarkerOptions().position(event).title(eventName).icon(BitmapDescriptorFactory.fromBitmap(icon)));





        float zoomRatio = 17.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fct, zoomRatio));
    }
}

