package com.example.app.userScreen.createEvents.vernisaje;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.createEvents.petreceri.PetreceriPage1;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage2;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage3;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage4;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage5;

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
}
