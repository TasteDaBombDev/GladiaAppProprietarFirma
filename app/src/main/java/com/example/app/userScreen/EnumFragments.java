package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.createEvents.Evenimente;
import com.example.app.userScreen.events.ListEvents;
import com.example.app.userScreen.profile.ProfileMainFragment;

public class EnumFragments extends FragmentPagerAdapter {

    private Evenimente evenimente = new Evenimente();
    private ListEvents listEvents = new ListEvents();
    private ProfileMainFragment profileMainFragment = new ProfileMainFragment();

    public EnumFragments(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 :
                return profileMainFragment;
            case 1 :
                return evenimente;
            case 2:
                return listEvents;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
