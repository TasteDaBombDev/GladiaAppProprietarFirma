package com.example.app.userScreen.events.previzEvent;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EnumFragmentsPrevizEvent extends FragmentPagerAdapter {

    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragmentsPrevizEvent(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return PrevizEvent.getINSTANCE();
        else if (position == 1)
            return Stats.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}