package com.example.app.userScreen.events.previzEvent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.example.app.userScreen.events.previzEvent.previzEventPetrecere.PrevizEventPetreceri;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class Stats extends Fragment implements OnMapReadyCallback {

    private static Stats INSTANCE = null;
    private static TextView t;
    private static ImageView i;
    private static View view;
    private static Transformation transformation;
    private MapView gMapView;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ConstraintLayout clip;
    private ImageView buss;
    private boolean on = false;

    public Stats(){

    }

    public static Stats getINSTANCE() {
        if(INSTANCE == null)
            INSTANCE = new Stats();
        return INSTANCE;
    }

    public static void resetINSTANCE(){ INSTANCE = null; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stats, container, false);

        gMapView = view.findViewById(R.id.map);
        gMapView.getMapAsync(this);
        gMapView.onCreate(getArguments());

        t = view.findViewById(R.id.numeEvent);
        i = view.findViewById(R.id.previzImgMap);
        transformation = new MaskTransformation(getContext(), R.drawable.circle);

        clip = view.findViewById(R.id.profile);
        buss = view.findViewById(R.id.bussinessIcon);

        buss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!on){
                    clip.setVisibility(View.GONE);
                    on = true;
                }
                else{
                    clip.setVisibility(View.VISIBLE);
                    on = false;
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (gMapView != null)
            gMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (gMapView != null)
            gMapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (gMapView != null)
            gMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (gMapView != null)
            gMapView.onStop();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (gMapView != null)
            gMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setAllGesturesEnabled(false);
        goToLoc(PrevizEventPetreceri.getLat(), PrevizEventPetreceri.getLng());
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
    }

    public void goToLoc(double lat, double lng){
        LatLng loc = new LatLng(lat,lng);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,16));
    }

    public static void setUp(ArrayList<String> text, Context c){
        t.setText(text.get(0));
        Picasso.get().load(text.get(1)).transform(transformation).into(i);

        LinearLayout root = view.findViewById(R.id.optionsRoot);
        root.removeAllViews();
        for (int j = 2; j < text.size(); j++) {
            TextView txt = new TextView(c);
            txt.setText(text.get(j));
            root.addView(txt);
        }

    }
}
