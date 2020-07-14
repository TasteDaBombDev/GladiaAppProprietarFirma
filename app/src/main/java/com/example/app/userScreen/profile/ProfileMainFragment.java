package com.example.app.userScreen.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;

public class ProfileMainFragment extends Fragment {

    private View view;
    private static ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_main_fragment, container, false);
        viewPager = view.findViewById(R.id.viewPagerProfile);
        EnumProfiles enumProfiles = new EnumProfiles(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(enumProfiles);
        viewPager.setCurrentItem(1);

        return view;
    }

    public static ViewPager getViewPager() {
        return viewPager;
    }
}
