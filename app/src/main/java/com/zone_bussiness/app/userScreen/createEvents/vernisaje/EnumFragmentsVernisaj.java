package com.zone_bussiness.app.userScreen.createEvents.vernisaje;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EnumFragmentsVernisaj extends FragmentPagerAdapter {

    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragmentsVernisaj(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0)
            return VernisajPage1.getINSTANCE();
        else if(position == 1)
            return VernisajPage2.getINSTANCE();
        else if(position == 2)
            return VernisajPage3.getINSTANCE();
        else if(position == 3)
            return VernisajPage4.getINSTANCE();
//        else if(position == 4)
//            return PetreceriPage5.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public static void destroy(){
        VernisajPage1.resetINSTANCE();
        VernisajPage2.resetINSTANCE();
        VernisajPage3.resetINSTANCE();
        VernisajPage3.resetINSTANCE();
    }
}
