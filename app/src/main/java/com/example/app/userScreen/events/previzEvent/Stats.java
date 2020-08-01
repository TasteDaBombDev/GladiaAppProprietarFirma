package com.example.app.userScreen.events.previzEvent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.events.previzEvent.previzEventPetrecere.PrevizEventPetreceri;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class Stats extends Fragment implements OnMapReadyCallback {

    @SuppressLint("StaticFieldLeak")
    private static Stats INSTANCE = null;
    private static TextView t;
    private static ImageView i;
    private static View view;
    private MapView gMapView;
    private static GoogleMap gMap;
    private ConstraintLayout clip;
    private ImageView buss;
    private boolean on = false;
    private static Resources r;
    private static boolean mapOK = false;

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

        clip = view.findViewById(R.id.profile);
        buss = view.findViewById(R.id.bussinessIcon);
        r = getResources();

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (gMapView != null)
            gMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setAllGesturesEnabled(false);
        mapOK = true;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
    }

    public static void goToLoc(final Context c, final double lat, final double lng){
        if(mapOK){
            LatLng loc = new LatLng(lat,lng);
            MarkerOptions m = new MarkerOptions().position(loc)
                    .icon(bitmapDescriptorFromVector(c, R.drawable.ic_location_on_black_24dp, 100,100));
            gMap.addMarker(m);
            gMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15));
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatLng loc = new LatLng(lat,lng);
                    MarkerOptions m = new MarkerOptions().position(loc)
                            .icon(bitmapDescriptorFromVector(c, R.drawable.ic_location_on_black_24dp, 100,100));
                    gMap.addMarker(m);
                    gMap.animateCamera(CameraUpdateFactory.newLatLng(loc));
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15));
                }
            }, 1000);
        }
        Log.e("lkat", lat + "");
        Log.e("lng", lng + "");
    }

    private static BitmapDescriptor bitmapDescriptorFromVector(Context c, int vectorResource, int width, int height){
        Drawable vectorDrawable = ContextCompat.getDrawable(c, vectorResource);
        vectorDrawable.setBounds(0,0, width, height);
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(b);
    }

    public static void setUp(ArrayList<String> text, Context c){
        t.setText(text.get(0));
        Picasso.get().load(text.get(1)).into(i);
        setImageRounded();

        LinearLayout root = view.findViewById(R.id.optionsRoot);
        root.removeAllViews();
        for (int j = 2; j < text.size(); j++) {
            TextView txt = new TextView(c);
            txt.setPadding(0,20,0,0);
            txt.setText(text.get(j));
            root.addView(txt);
        }

    }

    private static void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)i.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(r,bitmap);
        roundedBitmapDrawable.setCircular(true);
        i.setImageDrawable(roundedBitmapDrawable);
    }
}
