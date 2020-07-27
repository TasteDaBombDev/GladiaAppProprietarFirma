package com.example.app.userScreen.createEvents.concerte;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.R;

import java.util.ArrayList;

public class ConcertePage2 extends Fragment {
    private static ConcertePage2 INSTANCE = null;
    private View view;
    private static ArrayList<String> repertoriu;

    public ConcertePage2(){
    }

    public static ConcertePage2 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ConcertePage2();
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
        view = inflater.inflate(R.layout.concerte_page2, container, false);
        return view;
    }

    public static String getRepertoriu(){
        return repertoriu.toString();
    }
}
