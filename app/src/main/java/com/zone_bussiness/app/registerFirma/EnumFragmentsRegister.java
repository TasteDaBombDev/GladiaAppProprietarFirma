package com.zone_bussiness.app.registerFirma;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EnumFragmentsRegister  extends FragmentPagerAdapter {


    private Context context;
    private boolean locatie;

    @SuppressWarnings("deprecation")
    public EnumFragmentsRegister(@NonNull FragmentManager fm, Context context, boolean locatie) {
        super(fm);
        this.context = context;
        this.locatie = locatie;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return Register1.getINSTANCE(locatie);
        else if (position == 1)
            return Register2.getINSTANCE();
        else if (position == 2)
            if(locatie)
                return Register3.getINSTANCE();
            else
                return RegsiterDescriereOrganizatie.getINSTANCE();
        else if (position == 3)
            return Register4.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        if(locatie)
            return 4;
        return 3;
    }
}