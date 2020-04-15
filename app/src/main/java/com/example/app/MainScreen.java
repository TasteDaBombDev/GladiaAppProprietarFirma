package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

public class MainScreen extends AppCompatActivity {

    private static String username;
    private ViewPager viewPager;
    private EnumFragments enumFragments;
    private RelativeLayout root;
    private static int userID;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");
        username = extras.getString("username");

        viewPager = findViewById(R.id.mainSlider);
        root = findViewById(R.id.root);
        EnumFragments enumFragments = new EnumFragments(getSupportFragmentManager(),this);
        viewPager.setAdapter(enumFragments);

//        Toast.makeText(getApplicationContext(),googleMap + "", Toast.LENGTH_LONG).show();
//        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.activity_map,null);
//        root.addView(view);


    }

    public void swipeRight(int x){
        if(x < 2){
            viewPager.setCurrentItem(x + 1);
        }
    }

    public void swipeLeft(int x){
        if(x > 0){
            viewPager.setCurrentItem(x - 1);
        }
    }

    public static int getUserID() {
        return userID;
    }

    public static String getUsername() {
        return username;
    }

    @Override
    public void onBackPressed() {
        //Nothing
    }
}
