package com.zone_bussiness.app.userScreen.profile.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.MainScreen;
import com.zone_bussiness.app.userScreen.profile.ProfileLocation;
import com.zone_bussiness.app.utils.EventDetails;
import com.zone_bussiness.app.utils.ServerData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class Dashboard extends Fragment implements OnMapReadyCallback {

    private View view;
    private MapView gMapView;
    private GoogleMap gMap;
    private Boolean profileDisplayed = false;
    private ConstraintLayout profile;
    private static LinearLayout optionRoot;

    public Dashboard() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        init();

        gMapView = view.findViewById(R.id.map);
        gMapView.getMapAsync(this);
        gMapView.onCreate(getArguments());

        profile = view.findViewById(R.id.profile);
        profile.setVisibility(View.GONE);

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
        gMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));


        MarkerOptions m = new MarkerOptions().position(new LatLng(MainScreen.getLat(), MainScreen.getLng()))
                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_location_on_black_24dp, 100,100));

        final Marker markerM = gMap.addMarker(m);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(MainScreen.getLat(), MainScreen.getLng())));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MainScreen.getLat(), MainScreen.getLng()),16));

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!profileDisplayed){
                    profile.setVisibility(View.VISIBLE);
                    profileDisplayed = true;
                } else {
                    profile.setVisibility(View.GONE);
                    profileDisplayed = false;
                }
                return false;
            }
        });

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context c, int vectorResource, int width, int height){
        Drawable vectorDrawable = ContextCompat.getDrawable(c, vectorResource);
        vectorDrawable.setBounds(0,0, width, height);
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(b);

    }

    @SuppressLint("StaticFieldLeak")
    private static TextView t, eventName, indicator;
    @SuppressLint("StaticFieldLeak")
    private static ImageView i, iconParty;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout partyRoot;

    private void init(){
        i = view.findViewById(R.id.coverPic);
        t = view.findViewById(R.id.firmName);
        iconParty = view.findViewById(R.id.iconParty);
        eventName = view.findViewById(R.id.eventName);
        indicator = view.findViewById(R.id.indicator);
        partyRoot = view.findViewById(R.id.partyRoot);
        optionRoot = view.findViewById(R.id.optionsRoot);
    }

    public static void constructProfile(Context context){
        boolean ok = false;
        String date = new SimpleDateFormat("ddMMyyyyHHmm", Locale.getDefault()).format(new Date());
        t.setText(MainScreen.getNume());

        Transformation transformation = new MaskTransformation(context, R.drawable.circle);
        Picasso.get().load(MainScreen.getPozaPath()).transform(transformation).into(i);

        ArrayList<EventDetails> a = ServerData.getEventsInfo();
        for (int j = 0; j < a.size(); j++) {
            String d = a.get(j).getDate().replace("/","");
            String time = a.get(j).getHour();
            String time1 = time.substring(0,5), time2 = time.substring(8);
            time1 = time1.replace(":", "");
            time2 = time2.replace(":", "");
            String date1 = d + time1;
            String date2 = d + time2;
            if(date.compareTo(date1) > 0 && date.compareTo(date2) < 0){
                eventName.setText(a.get(j).getName());
                Picasso.get().load(a.get(j).getImgPath()).into(iconParty);
                ok = true;
                break;
            }
        }

        if(ok){
            indicator.setVisibility(View.VISIBLE);
            partyRoot.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
            partyRoot.setVisibility(View.GONE);
        }

        optionRoot.removeAllViews();
        for (int j = 0; j < ProfileLocation.getFirmaDetails().size(); j++) {
            TextView tt = new TextView(context);
            tt.setText(ProfileLocation.getFirmaDetails().get(j));
            optionRoot.addView(tt);
        }



    }
}
