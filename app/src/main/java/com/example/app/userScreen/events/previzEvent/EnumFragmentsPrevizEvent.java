package com.example.app.userScreen.events.previzEvent;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.events.previzEvent.previzEventConcert.PrevizEventConcert;
import com.example.app.userScreen.events.previzEvent.previzEventPetrecere.PrevizEventPetreceri;
import com.example.app.userScreen.events.previzEvent.previzEventVernisaj.PrevizEventVernisaj;

public class EnumFragmentsPrevizEvent extends FragmentPagerAdapter {

    private Context context;
    private String type;

    @SuppressWarnings("deprecation")
    public EnumFragmentsPrevizEvent(@NonNull FragmentManager fm, Context context, String type) {
        super(fm);
        this.context = context;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if(type.equals("petrecere"))
                return PrevizEventPetreceri.getINSTANCE();
            else if(type.equals("vernisaj"))
                return PrevizEventVernisaj.getINSTANCE();
            else if(type.equals("concert"))
                return PrevizEventConcert.getINSTANCE();
        } else if (position == 1)
            return Stats.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}