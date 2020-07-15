package com.example.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.profile.dashboard.Dashboard;

public class EnumProfiles extends FragmentPagerAdapter {

    private ProfileLocation profileLocation = new ProfileLocation();
    private ProfileOrganisation profileOrganisation = new ProfileOrganisation();
    private Dashboard dashboard = new Dashboard();
    private Sales sales = new Sales();
    private static boolean isLocation;

    public EnumProfiles(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return dashboard;
        else
            if(position == 1){
                if(MainScreen.getCod().substring(2,3).equals("L")) {
                    isLocation = true;
                    return profileLocation;
                }
                else{
                    isLocation = false;
                    return profileOrganisation;
                }
            } else if(position == 2)
                return sales;
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public static boolean isLocation(){
        return isLocation;
    }
}
