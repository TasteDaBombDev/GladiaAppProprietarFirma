package com.example.app.userScreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;
import com.google.android.material.tabs.TabLayout;

public class MainScreen extends AppCompatActivity {

    private static ViewPager viewPager;
    private TabLayout tabLayout;
    private static int[] viewPagersPages = {5,0,0,0,0,0};

    private static int userID;
    private static String nume, pozaPath, password, mail, type, dressCode, tema,  path, adresa;
    private static double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen);

        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");
        pozaPath = extras.getString("pozaPath");
        nume = extras.getString("nume");
        password = extras.getString("password");
        mail = extras.getString("mail");
        type = extras.getString("type");
        dressCode = extras.getString("dressCode");
        tema = extras.getString("tema");
        path = extras.getString("logoutPath");
        adresa = extras.getString("adresa");
        lat = extras.getDouble("lat");
        lng = extras.getDouble("lng");

        viewPager = findViewById(R.id.mainSlider);

        EnumFragments enumFragments = new EnumFragments(getSupportFragmentManager(),this);
        viewPager.setAdapter(enumFragments);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.plus);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);

    }

    public static int getUserID() {
        return userID;
    }

    public static String getNume() {
        return nume;
    }

    public static String getPassword() {
        return password;
    }

    public static String getMail() {
        return mail;
    }

    public static String getPath() {
        return path;
    }

    public static String getPozaPath() {
        return pozaPath;
    }

    public static String getType() {
        return type;
    }

    public static String getDressCode() {
        return dressCode;
    }

    public static String getTema() {
        return tema;
    }

    public static String getAdresa() {
        return adresa;
    }

    public static double getLat() {
        return lat;
    }

    public static double getLng() {
        return lng;
    }

    public static ViewPager getViewPager(){
        return viewPager;
    }

    public static int getViewPagerCountPage(int i){
        return viewPagersPages[i];
    }

    @Override
    public void onBackPressed() {
        //Nothing
    }
}
