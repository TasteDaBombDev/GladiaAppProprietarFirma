package com.example.app.userScreen.createEvents.petreceri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.userScreen.events.previzEvent.PrevizEvent;
import com.example.app.utils.Pairs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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
    private GoogleMap map;
    private static String address = "";
    private EditText searchLocation;
    private ListView addressList;
    private ConstraintLayout root;
    private LinearLayout completeResults;
    private ArrayAdapter arrayAdapter;
    private Pairs<String,LatLng> addressesList = new Pairs<>();
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);
        extras = getIntent().getExtras();
        done = findViewById(R.id.done);
        completeResults = findViewById(R.id.completeResults);
        root = findViewById(R.id.root);
        TransitionManager.beginDelayedTransition(root);

        addressList = findViewById(R.id.addressList);
        arrayAdapter = new ArrayAdapter(this, R.layout.list_item,R.id.addressName,addressesList.getKey());
        addressList.setAdapter(arrayAdapter);
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng pos = addressesList.getOneVal(position);
                map.animateCamera(CameraUpdateFactory.newLatLng(pos));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,19));
                completeResults.setVisibility(View.INVISIBLE);
                addressesList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        searchLocation = findViewById(R.id.searchLocation);
        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressesList.clear();
                arrayAdapter.notifyDataSetChanged();
                completeResults.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if(address.equals(""))
                   if(currentLocation == null) {
                       LatLng loc = new LatLng(0.0f,0.0f);
                       googleMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,4));
                   } else {
                       LatLng loc = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                       googleMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,14));
                   }
               else
                   geolocateAdress(address);

                searchLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                                event.getAction() == KeyEvent.ACTION_DOWN ||
                                event.getAction() == KeyEvent.KEYCODE_ENTER){
                            addressesList.clear();
                            arrayAdapter.notifyDataSetChanged();
                            completeResults.setVisibility(View.INVISIBLE);
                            geoLocation(searchLocation.getText().toString().trim());
                            arrayAdapter.notifyDataSetChanged();
                            completeResults.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lat = map.getCameraPosition().target.latitude;
                        lng = map.getCameraPosition().target.longitude;
                        Geocoder geocoder = new Geocoder(SelectLocation.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
                            address = addresses.get(0).getAddressLine(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        switch (extras.getInt("redirectedPage")){
                            case 1:
                                PetreceriPage1.updateValue();
                                break;
                            case -1:
                                PrevizEvent.setAdr(address);
                                break;
                        }
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

    public void geoLocation(String searchAddress){
        Geocoder geocoder = new Geocoder(SelectLocation.this);

        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchAddress, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                addressesList.add(list.get(i).getAddressLine(0),new LatLng(list.get(i).getLatitude(),list.get(i).getLongitude()));
            }
        } else {
            addressesList.add("Adresa nu a fost gasita.", null);
        }
    }

    private void geolocateAdress(String adr){
        Geocoder geocoder = new Geocoder(SelectLocation.this);

        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(adr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng loc = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLng(loc));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,14));

    }

    public static double getLat() {
        return lat;
    }

    public static double getLng() {
        return lng;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        SelectLocation.address = address;
    }
}
