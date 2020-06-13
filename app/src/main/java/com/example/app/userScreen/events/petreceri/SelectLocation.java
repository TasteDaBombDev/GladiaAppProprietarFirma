package com.example.app.userScreen.events.petreceri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.GetChars;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectLocation extends AppCompatActivity implements OnMapReadyCallback {

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private Button done;
    private static double lng = 0, lat = 0;
    private static boolean visited = false;
    private GoogleMap map;
    private static String address;
    private ListView addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);
        done = findViewById(R.id.done);
        addressList = findViewById(R.id.addressList);
        ArrayList<String> addressesList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_item,R.id.addressName,addressesList);
        addressList.setAdapter(arrayAdapter);

        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");
        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");
        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");
        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");
        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");
        addressesList.add("mama");
        addressesList.add("tata");
        addressesList.add("Petru e prost");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLastLocation();
                done.setClickable(true);
            }
        },300);

    }

    public void getLastLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                    currentLocation = location;
                else
                    Toast.makeText(SelectLocation.this, "Deschide-ti locatia", Toast.LENGTH_SHORT).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(SelectLocation.this);
            }
        });
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentLocation == null) {
                    LatLng loc = new LatLng(0.0f,0.0f);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,4));
                } else {
                    LatLng loc = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,14));
                }

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lat = map.getCameraPosition().target.latitude;
                        lng = map.getCameraPosition().target.longitude;
                        visited = true;
                        Geocoder geocoder = new Geocoder(SelectLocation.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
                            address = addresses.get(0).getAddressLine(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        PetreceriPage1.updateValue();
                        onBackPressed();
                    }
                });
            }
        },300);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLastLocation();
                }
                break;
        }
    }

    public static double getLat() {
        return lat;
    }

    public static double getLng() {
        return lng;
    }

    public static boolean getVisited(){
        return visited;
    }

    public static String getAddress() {
        return address;
    }
}
