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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConcertePage2 extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static ConcertePage2 INSTANCE = null;
    private static TextInputLayout tematicaLayout;
    private static TextInputEditText tematica;
    private TextInputLayout descriereLayout;
    private static TextInputEditText descriere;
    private View view;


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
        view = inflater.inflate(R.layout.concerte_page2,container,false);
        init();
        focusListener();

        return view;
    }

    private void init(){
        tematicaLayout = view.findViewById(R.id.tematicaLayoutVernisaj);
        tematica = view.findViewById(R.id.tematicaVernisaj);

        descriereLayout = view.findViewById(R.id.descriereLayoutVernisaj);
        descriere = view.findViewById(R.id.descriereVernisaj);
    }

    private void focusListener(){
        tematica.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    tematicaLayout.setCounterEnabled(true);
                    tematicaLayout.setCounterMaxLength(20);
                }
                else
                {
                    tematicaLayout.setCounterEnabled(false);
                    tematicaLayout.setCounterMaxLength(0);
                }
            }
        });

        descriere.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    descriereLayout.setCounterEnabled(true);
                    descriereLayout.setCounterMaxLength(500);
                }
                else
                {
                    descriereLayout.setCounterEnabled(false);
                    descriereLayout.setCounterMaxLength(0);
                }
            }
        });
    }

    public static String getDescriere(){
        return descriere.getText().toString();
    }

    public static String getTematica(){
        return tematica.getText().toString();
    }

    public static void reset(){
        descriere.setText("");
        tematica.setText("");
    }
}
