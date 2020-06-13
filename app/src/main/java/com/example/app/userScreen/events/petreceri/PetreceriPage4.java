package com.example.app.userScreen.events.petreceri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;
import com.wefika.flowlayout.FlowLayout;

public class PetreceriPage4 extends Fragment {

    private static PetreceriPage4 INSTANCE = null;
    private Button mancare, bautura;
    private boolean mancareB = false, bauturaB = false;
    private EditText mancarePret, bauturaPret;
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
    }
}
