package com.zone_bussiness.app.userScreen;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.utils.OurViewPager;
import com.google.android.material.tabs.TabLayout;

public class MainScreen extends AppCompatActivity {

    private static OurViewPager viewPager;
    private TabLayout tabLayout;
    private static int[] viewPagersPages = {5,4,5,0};

    private static int userID;
    private static String descriere, menu, Cod, muzica, decor, numeFirma, nume, pozaPath, password, mail, type, dressCode, tema,  path, adresa;
    private static double lat,lng;


    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen);
        getExtras();


        viewPager = findViewById(R.id.mainSlider);
        EnumFragments enumFragments = new EnumFragments(getSupportFragmentManager());
        viewPager.setAdapter(enumFragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        viewPager.setPagingEnabled(false);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.plus);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
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

    public static String getDecor() {
        return decor;
    }

    public static String getMuzica() {
        return muzica;
    }

    public static String getMenu() {
        return menu;
    }

    public static String getDescriere() {
        return descriere;
    }

    public static String getNumeFirma() {
        return numeFirma;
    }

    public static String getCod() {
        return Cod;
    }

    public static OurViewPager getViewPager(){
        return viewPager;
    }

    public static int getViewPagerCountPage(int i){
        return viewPagersPages[i];
    }

    @Override
    public void onBackPressed() {
        //Nothing
    }

    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");
        muzica = extras.getString("muzica");
        decor = extras.getString("decor");
        Cod = extras.getString("COD");
        pozaPath = extras.getString("pozaPath");
        nume = extras.getString("nume");
        numeFirma = extras.getString("numeFirma");
        password = extras.getString("password");
        mail = extras.getString("mail");
        type = extras.getString("type");
        dressCode = extras.getString("dressCode");
        tema = extras.getString("tema");
        path = extras.getString("logoutPath");
        adresa = extras.getString("adresa");
        lat = extras.getDouble("lat");
        lng = extras.getDouble("lng");
        menu = extras.getString("menu");
        descriere = extras.getString("descriere");
    }
}
