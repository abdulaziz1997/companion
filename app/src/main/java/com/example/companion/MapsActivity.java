package com.example.companion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    int place_id, city, city_id;
    float lat, lon;
    int language;
    String select, tName, ustun[], obyekt_nomi;
    static final String DATABASE_NAME = "database";
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent a = getIntent();
        place_id = a.getIntExtra("place_id", -1);
        city = a.getIntExtra("city", -1);
        language = a.getIntExtra("language", -1);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DATABASE_NAME);
        database = dbOpenHelper.openDataBase();

        if(place_id != -1){
            select = "id_obyekt = " + String.valueOf(place_id);
            tName = "Obyekt";
            ustun = new String[5];
            String[] ustun1 = {"lat", "long", "o_nomi_uz", "o_nomi_eng", "o_nomi_ru"};
            ustun = ustun1;
        }
        if(city != -1){
            if(city == 0){city_id = 1;}
            else if(city == 1){city_id = 3;}
            else if(city == 2){city_id = 6;}
            else if(city == 3){city_id = 10;}
            select = "id_shahar = " + String.valueOf(city_id);
            tName = "Shahar";
            ustun = new String[5];
            String[] ustun2 = {"lat", "long"};
            ustun = ustun2;
        }

        Cursor friendCursor = database.query(tName,
                ustun,
                select, null, null, null, null);


        /*Cursor friendCursor = database.query(tName,
                new String[]
                        {"lat", "long"},
                select, null, null, null, null);*/
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                if(place_id != -1){

                    switch(language) {
                        case 0:
                            obyekt_nomi = friendCursor.getString(2);

                            break;
                        case 1:
                            obyekt_nomi = friendCursor.getString(3);

                            break;
                        case 2:
                            obyekt_nomi = friendCursor.getString(4);

                            break;}

                    lat = friendCursor.getFloat(0);
                    lon = friendCursor.getFloat(1);

                }

                if(city != -1){
                    lat = friendCursor.getFloat(0);
                    lon = friendCursor.getFloat(1);
                }
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
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

        LatLng place = new LatLng(lat, lon);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon)).zoom(10)
                .build();

        mMap.setOnInfoWindowClickListener(this);

        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);

        if(place_id != -1) {
            mMap.addMarker(new MarkerOptions().position(place).title(obyekt_nomi));
        }

        mMap.moveCamera(cameraUpdate);

        mMap.setMyLocationEnabled(true);
    }





    public void onMapBackClick(View view){
        finish();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this,obyekt_nomi,
                Toast.LENGTH_LONG).show();

    }
}
