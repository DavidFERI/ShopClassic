package com.feri.david.com.shoppingcenter;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.DataAll;
import com.example.Prodajalna;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DataAll all;
    Prodajalna pro;

    double latitude;
    double longitude;
    private Integer indeks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        all = DataAll.getScenarij1Data();
        Intent intent = getIntent();
        if (intent!=null) {
            indeks = intent.getIntExtra(AdapterOblacil.PARAMETER_POSITION,0); //id
            System.out.println("test indeks: " + indeks);
        }
        pro = all.getProd(indeks);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Address> geocodeMatches = null;

        try {
            geocodeMatches = new Geocoder(this).getFromLocationName(pro.getLokacija().getHisna() + pro.getLokacija().getNaslov() + pro.getLokacija().getPostna_st(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (geocodeMatches != null) {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();

        }
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            LatLng sydney = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title(pro.getNaziv()));
            LatLng sydney1 = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );

    }
}
