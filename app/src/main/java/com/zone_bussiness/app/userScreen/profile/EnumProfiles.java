package com.zone_bussiness.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zone_bussiness.app.userScreen.MainScreen;
import com.zone_bussiness.app.userScreen.profile.dashboard.Dashboard;

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
        if(position == 2)
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
            } else if(position == 0)
                return sales;
        return null;
    }

    @Override
    public int getCount() {
        if(MainScreen.getCod().substring(2,3).equals("L"))
            return 3;
        return 2;
    }

    public static boolean isLocation(){
        return isLocation;
    }
}
