package com.zone_bussiness.app.userScreen.createEvents.petreceri;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zone_bussiness.app.R;

public class PetreceriPage5 extends Fragment {

    private static PetreceriPage5 INSTANCE = null;

    private View view;

    public PetreceriPage5(){

    }

    public static PetreceriPage5 getINSTANCE() {
        if(INSTANCE == null)
            INSTANCE = new PetreceriPage5();
        return INSTANCE;
    }

    public static void resetINSTANCE(){INSTANCE = null;}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.petreceri_page5, container, false);
        init();
        return view;

    }

    private void init(){

    }
}
