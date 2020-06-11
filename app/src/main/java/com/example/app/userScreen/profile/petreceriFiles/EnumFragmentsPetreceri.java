package com.example.app.userScreen.profile.petreceriFiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EnumFragmentsPetreceri extends FragmentPagerAdapter {

    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragmentsPetreceri(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0)
            return PetreceriPage1.getINSTANCE();
        else if(position == 1)
            return PetreceriPage2.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
