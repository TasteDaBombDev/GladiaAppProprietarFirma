package com.example.app.userScreen.createEvents.concerte;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.app.R;

public class ConcertePage5 extends Fragment {

    private static ConcertePage5 INSTANCE = null;
    private static Button mancare, bautura, bilet;
    private static boolean mancareB = false, bauturaB = false, biletB = false;
    private static EditText biletPret, mancarePret, bauturaPret;
    private static Switch aSwitch;
    private View view;

    public ConcertePage5(){
    }

    public static ConcertePage5 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ConcertePage5();
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
        view = inflater.inflate(R.layout.concerte_page5, container, false);
        init();

        mancarePret.setEnabled(false);
        bauturaPret.setEnabled(false);
        biletPret.setEnabled(false);

        mancare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mancareB)
                {
                    mancareB = true;
                    mancare.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
                    mancarePret.setEnabled(true);
                }
                else{
                    mancare.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                    mancareB = false;
                    mancarePret.setEnabled(false);
                }
            }
        });

        bilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!biletB)
                {
                    biletB = true;
                    bilet.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
                    biletPret.setEnabled(true);
                }
                else{
                    bilet.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                    biletB = false;
                    biletPret.setEnabled(false);
                }
            }
        });

        bautura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bauturaB)
                {
                    bauturaB = true;
                    bautura.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
                    bauturaPret.setEnabled(true);
                }
                else{
                    bautura.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                    bauturaB = false;
                    bauturaPret.setEnabled(false);
                }
            }
        });

        return view;
    }




    private void init(){
        bautura = view.findViewById(R.id.bautura);
        mancare = view.findViewById(R.id.mancare);
        bilet = view.findViewById(R.id.bilet);

        mancarePret = view.findViewById(R.id.mancarePret);
        bauturaPret = view.findViewById(R.id.bauturaPret);
        biletPret = view.findViewById(R.id.biletPret);

        aSwitch = view.findViewById(R.id.outdorIndor);
    }

    public static int getMancare(){
        if(mancareB)
            return 1;
        return 0;
    }

    public static int getBautura(){
        if(bauturaB)
            return 1;
        return 0;
    }

    public static int getBilet() {
        if(biletB)
            return 1;
        return 0;
    }

    public static String getBiletPret(){
        if(biletB)
            return biletPret.getText().toString();
        return "";
    }

    public static String getMancarePret(){
        if(mancareB)
            return mancarePret.getText().toString();
        return "";
    }

    public static String getBauturaPret(){
        if(bauturaB)
            return bauturaPret.getText().toString();
        return "";
    }

    public static void reset(){
        bauturaPret.setText("");
        bauturaB = false;
        mancareB = false;
        mancarePret.setText("");
        biletPret.setText("");
    }

    public static Button getBauturaButton(){
        return bautura;
    }

    public static Button getMancareButton(){
        return mancare;
    }

    public static boolean getindooroutdoorMode() {
        return aSwitch.isChecked();
    }
}
