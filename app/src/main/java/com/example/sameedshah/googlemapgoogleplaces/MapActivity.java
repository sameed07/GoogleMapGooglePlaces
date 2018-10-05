package com.example.sameedshah.googlemapgoogleplaces;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST = 1;

    GoogleMap mMap;


    private boolean mLocationPermissionsGranted = false;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onMapReady: map is ready here");
        mMap = googleMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermissions();
    }

    private void getLocationPermissions(){

        Log.d(TAG,"gettingLocationPermissions : map is ready here");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationPermissionsGranted = true;
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST);
        }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST);
        }
    }
    private void initMap(){

        Log.d(TAG,"initMap: map is ready here");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;

        switch (requestCode){

            case LOCATION_PERMISSION_REQUEST:
            {
               if(grantResults.length > 0){

                   for(int i = 0; i < grantResults.length; i ++){

                       if(grantResults[i] != PackageManager.PERMISSION_GRANTED){

                           mLocationPermissionsGranted = false;
                           Log.d(TAG, "onRequestPermissionsResult: Permission Failed");
                           return;
                       }
                   }

                   Log.d(TAG, "onRequestPermissionsResult: Permission granted");
                   mLocationPermissionsGranted = true;

                   //init map

                   initMap();
               }
            }
        }
    }


}