package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Fragment implements OnMapReadyCallback {

    private static MapActivity INSTANCE = null;

    private View view;

    private static GoogleMap map;
    private MapView mapView;
    private LatLng sydney = new LatLng(43.1, -87.9);


    public MapActivity(){
    }

    public static MapActivity getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new MapActivity();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map,container,false);


        ConstraintLayout aggroZoneProfile = view.findViewById(R.id.toProfile), aggroZoneEvents = view.findViewById(R.id.toEvents);

        aggroZoneEvents.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    map.getUiSettings().setScrollGesturesEnabled(false);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    map.getUiSettings().setScrollGesturesEnabled(true);
                    return false;
                }
                return false;
            }
        });


        aggroZoneProfile.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    map.getUiSettings().setScrollGesturesEnabled(false);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    map.getUiSettings().setScrollGesturesEnabled(true);
                    return false;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;

        MarkerOptions markerOptions = new MarkerOptions().position(sydney).title("Me");

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));

        googleMap.addMarker(markerOptions);
    }

    //    private GoogleMap map;
//    private Location me;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int REQUEST_CODE = 101;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        Toast.makeText(this, "hey din onCreate", Toast.LENGTH_SHORT).show();
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//    }
//
//    private void getLastLocation() {
//        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
//            return;
//        }
//        Toast.makeText(getContext(), "hey...", Toast.LENGTH_SHORT).show();
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if(location != null){
//                    me = location;
//                    SupportMapFragment mapFragment = (SupportMapFragment) getParentFragmentManager().findFragmentById(R.id.mapView);
//                    mapFragment.getMapAsync(MapActivity.this);
//                }
//                else
//                    Toast.makeText(getContext(), "Deschide-ti locatia", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//
//        MapStyleOptions mapStyleOptions= MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style);
//        googleMap.setMapStyle(mapStyleOptions);
//
//        LatLng point = new LatLng(me.getLatitude(),me.getLongitude());
//
//        MarkerOptions markerOptions = new MarkerOptions().position(point).title("Me");
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point,16));
//
//        googleMap.addMarker(markerOptions);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            }
//        }
//    }
//
    public static GoogleMap getMap() {
        return map;
    }
}
