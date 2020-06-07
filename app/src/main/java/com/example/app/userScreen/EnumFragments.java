package com.example.app.userScreen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.profile.ProfileMainClass;

public class EnumFragments extends FragmentPagerAdapter {


    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragments(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0)
            return ProfileMainClass.getINSTANCE();
        else if(position == 1)
            return Evenimente.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
}
