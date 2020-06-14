package com.example.app.userScreen.events.petreceri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;

public class PetreceriPage4 extends Fragment {

    private static PetreceriPage4 INSTANCE = null;
    private Button mancare, bautura;
    private static boolean mancareB = false, bauturaB = false;
    private static EditText mancarePret, bauturaPret, biletPret, tinuta, descriere;
    private View view;

    public PetreceriPage4(){
    }

    public static PetreceriPage4 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new PetreceriPage4();
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
        view = inflater.inflate(R.layout.petreceri_page4, container, false);
        init();
        mancarePret.setEnabled(false);
        bauturaPret.setEnabled(false);

        mancare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mancareB)
                {
                    mancareB = true;
                    mancare.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.tinted));
                    mancarePret.setEnabled(true);
                }
                else{
                    mancare.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                    mancareB = false;
                    mancarePret.setEnabled(false);
                }
            }
        });

        bautura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bauturaB)
                {
                    bauturaB = true;
                    bautura.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.tinted));
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
        mancarePret = view.findViewById(R.id.mancarePret);
        bauturaPret = view.findViewById(R.id.bauturaPret);
        biletPret = view.findViewById(R.id.biletPret);
        tinuta = view.findViewById(R.id.tinuta);
        descriere = view.findViewById(R.id.descriere);
    }

    public static String getDescriere(){
        return descriere.getText().toString();
    }

    public static String getTinuta(){
        return tinuta.getText().toString();
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

    public static String getBiletPret(){
        return biletPret.getText().toString();
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

}
