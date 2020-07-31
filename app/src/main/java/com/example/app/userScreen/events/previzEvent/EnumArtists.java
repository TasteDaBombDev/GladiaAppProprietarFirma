package com.example.app.userScreen.events.previzEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class EnumArtists extends FragmentPagerAdapter {

    private ArrayList<String> paths;
    private ArrayList<String> names, descriere;

    public EnumArtists(@NonNull FragmentManager fm, ArrayList<String>  paths, ArrayList<String>  names, ArrayList<String>  descriere) {
        super(fm);
        this.paths = paths;
        this.names = names;
        this.descriere = descriere;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PageArtist.getINSTANCE(paths.get(position), names.get(position), descriere.get(position));
    }

    @Override
    public int getCount() {
        return paths.size();
    }
}
