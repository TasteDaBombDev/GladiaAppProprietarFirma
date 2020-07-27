package com.example.app.userScreen.createEvents.concerte;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.createEvents.vernisaje.VernisajPage1;
import com.example.app.userScreen.createEvents.vernisaje.VernisajPage2;
import com.example.app.userScreen.createEvents.vernisaje.VernisajPage3;
import com.example.app.userScreen.createEvents.vernisaje.VernisajPage4;

public class EnumFragmentsConcerte extends FragmentPagerAdapter {

    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragmentsConcerte(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0)
            return ConcertePage1.getINSTANCE();
        else if(position == 1)
            return ConcertePage2.getINSTANCE();
        else if(position == 2)
            return ConcertePage3.getINSTANCE();
        else if(position == 3)
            return ConcertePage4.getINSTANCE();
        else if(position == 4)
            return ConcertePage5.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public static void destroy(){
        ConcertePage1.resetINSTANCE();
        ConcertePage2.resetINSTANCE();
        ConcertePage3.resetINSTANCE();
        ConcertePage4.resetINSTANCE();
        ConcertePage5.resetINSTANCE();
    }
}
