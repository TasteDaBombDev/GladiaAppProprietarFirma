package com.example.app.userScreen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.events.Evenimente;
import com.example.app.userScreen.profile.ProfileLocation;
import com.example.app.userScreen.profile.ProfileOrganisation;

public class EnumFragments extends FragmentPagerAdapter {


    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragments(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
//        if(position == 0)
//            if(MainScreen.getCod().substring(2,3).equals("L"))
//                return ProfileLocation.getINSTANCE();
//            else
//                return ProfileOrganisation.getINSTANCE();
//        else if(position == 1)
//            return Evenimente.getINSTANCE();
//        else if(position == 2)
//            return ListEvents.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
