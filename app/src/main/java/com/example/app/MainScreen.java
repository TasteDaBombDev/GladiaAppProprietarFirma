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
    private RelativeLayout root;
    private static int userID;
    private GoogleMap googleMap;
//    private int actualPos = 0;
    private static String nume;
    private static String prenume;
    private static String password;
    private static String mail;
    private static String ziuaDeNastere;
    private static String sex;
    private static String nrtel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");
        username = extras.getString("username");
        nume = extras.getString("nume");
        prenume = extras.getString("prenume");
        password = extras.getString("password");
        mail = extras.getString("mail");
        ziuaDeNastere = extras.getString("ziuaDeNastere");
        sex = extras.getString("sex");
        nrtel = extras.getString("nrtel");

        viewPager = findViewById(R.id.mainSlider);
        root = findViewById(R.id.root);
        EnumFragments enumFragments = new EnumFragments(getSupportFragmentManager(),this);
        viewPager.setAdapter(enumFragments);
        viewPager.setCurrentItem(1);
        
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private boolean scrollStarted = false;


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                    MapActivity.getMap().getUiSettings().setScrollGesturesEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollStarted = true;
                    MapActivity.getMap().getUiSettings().setScrollGesturesEnabled(false);
                } else {
                    scrollStarted = false;
                    MapActivity.getMap().getUiSettings().setScrollGesturesEnabled(true);
                }
            }
        });
    }

//    public boolean theSame(){
//        if(viewPager.getCurrentItem() == actualPos)
//            return true;
//        return false;
//    }
//
//    public void swipeRight(int x){
//        if(x < 2){
//            viewPager.setCurrentItem(x + 1);
//            actualPos = x;
//        }
//    }
//
//    public void swipeLeft(int x){
//        if(x > 0){
//            viewPager.setCurrentItem(x - 1);
//            actualPos = x;
//        }
//    }

    public static int getUserID() {
        return userID;
    }

    public static String getUsername() {
        return username;
    }

    public static String getNume() {
        return nume;
    }

    public static String getPrenume() {
        return prenume;
    }

    public static String getPassword() {
        return password;
    }

    public static String getMail() {
        return mail;
    }

    public static String getZiuaDeNastere() {
        return ziuaDeNastere;
    }

    public static String getSex() {
        return sex;
    }

    public static String getNrtel() {
        return nrtel;
    }

    @Override
    public void onBackPressed() {
        //Nothing
    }
}
