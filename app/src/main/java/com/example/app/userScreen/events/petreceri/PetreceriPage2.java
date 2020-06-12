package com.example.app.userScreen.events.petreceri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;

public class PetreceriPage2 extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static PetreceriPage2 INSTANCE = null;

    private View view;


    public PetreceriPage2(){
    }

    public static PetreceriPage2 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new PetreceriPage2();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.petreceri_page2,container,false);

        return view;
    }

}
