package com.zone_bussiness.app.registerFirma;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.utils.Pairs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Register2 extends Fragment implements OnMapReadyCallback {

    private static Register2 INSTANCE = null;
    private View view;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private static double lng = 0.0f, lat = 0.0f;
    private GoogleMap map;
    private MapView mapView;
    private static String address = "";
    private EditText searchLocation;
    private ListView addressList;
    private LinearLayout completeResults;
    private ArrayAdapter arrayAdapter;
    private Pairs<String,LatLng> addressesList = new Pairs<>();
    private ImageButton edit, save;

    public Register2(){
    }

    public static Register2 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Register2();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register2, container, false);
        edit = view.findViewById(R.id.edit);
        save = view.findViewById(R.id.saveState);

        completeResults = view.findViewById(R.id.completeResults);

        addressList = view.findViewById(R.id.addressList);
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.list_item,R.id.addressName,addressesList.getKey());
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

        searchLocation = view.findViewById(R.id.searchLocation);
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLastLocation();
            }
        },300);

        return view;
    }


    public void getLastLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null)
                    currentLocation = location;
                else
                    Toast.makeText(getContext(), "Deschide-ti locatia", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        MapsInitializer.initialize(getContext());
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
            }
        },300);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.getUiSettings().setAllGesturesEnabled(true);
                searchLocation.setEnabled(true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lat = map.getCameraPosition().target.latitude;
                lng = map.getCameraPosition().target.longitude;
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
                    address = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.getUiSettings().setAllGesturesEnabled(false);
                searchLocation.setEnabled(false);
            }
        });
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
        Geocoder geocoder = new Geocoder(getContext());

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

    public static double getLng() {
        return lng;
    }

    public static double getLat() {
        return lat;
    }

    public static String getAddress() {
        return address;
    }

    public static boolean areCompleted(){
        if((lng == 0 && lat == 0) || address.trim().equals(""))
            return false;
        return true;
    }
}
