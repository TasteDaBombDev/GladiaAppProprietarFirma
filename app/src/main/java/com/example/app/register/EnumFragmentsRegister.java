package com.example.app.register;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EnumFragmentsRegister extends FragmentPagerAdapter {

    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragmentsRegister(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0)
            return RegisterDoi.getINSTANCE();
        else if(position == 1)
            return RegisterTrei.getINSTANCE();
        else if(position == 2)
            return RegisterFour.getINSTANCE();
        else
            return RegisterFive.getINSTANCE();
//        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
