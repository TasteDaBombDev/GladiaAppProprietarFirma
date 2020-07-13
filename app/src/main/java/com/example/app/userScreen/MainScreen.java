package com.example.app.userScreen;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;
import com.example.app.termsAndConditions.ZoomOutPageTransformer;
import com.example.app.userScreen.events.Evenimente;
import com.example.app.userScreen.profile.ProfileLocation;
import com.example.app.userScreen.profile.ProfileOrganisation;
import com.google.android.material.tabs.TabLayout;

public class MainScreen extends AppCompatActivity {

    private static ViewPager viewPager;
    private TabLayout tabLayout, tabLayout2;
    private static int[] viewPagersPages = {5,0,0,0,0,0};
    private ProfileLocation profileLocation = new ProfileLocation();
    private ProfileOrganisation profileOrganisation = new ProfileOrganisation();
    private Evenimente evenimente = new Evenimente();
    private ListEvents listEvents = new ListEvents();

    private static int userID;
    private static String descriere, menu, Cod, muzica, decor, numeFirma, nume, pozaPath, password, mail, type, dressCode, tema,  path, adresa;
    private static double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_screen);

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

        tabLayout = findViewById(R.id.tabLayout);

//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.plus);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragment();
                break;
            case 1 :
                replaceFragment(fragmentTwo);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
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
