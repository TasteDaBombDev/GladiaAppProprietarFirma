package com.zone_bussiness.app.registerFirma;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zone_bussiness.app.R;
import com.google.android.material.textfield.TextInputEditText;

public class Register3 extends Fragment {
    
    private static Register3 INSTANCE = null;
    private static TextInputEditText descriere, tema, dresscode, muzica, decor;
    private View view;

    public Register3(){
    }

    public static Register3 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Register3();
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
        view = inflater.inflate(R.layout.register3, container, false);

        tema = view.findViewById(R.id.tema);
        dresscode = view.findViewById(R.id.dressCode);
        muzica = view.findViewById(R.id.muzica);
        decor = view.findViewById(R.id.decor);
        descriere = view.findViewById(R.id.descriere);


        return view;
    }

    public static String getTema() {
        return tema.getText().toString().trim();
    }

    public static String getDressCode() {
        return dresscode.getText().toString().trim();
    }

    public static String getMuzica() {
        return muzica.getText().toString().trim();
    }

    public static String getDecor() {
        return decor.getText().toString().trim();
    }

    public static String getDescriere(){
        return descriere.getText().toString().trim();
    }
}
